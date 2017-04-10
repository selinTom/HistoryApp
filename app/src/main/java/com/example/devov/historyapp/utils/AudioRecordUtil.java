package com.example.devov.historyapp.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by devov on 2016/12/14.
 */

public class AudioRecordUtil {
    public final static int AUDIO_SAMPLE_RATE = 8000;
    private static final String AUDIO_RECORD_RAW_FILE=".pcm";
    private static final String AUDIO_RECORD_FILE=".wav";
    private int mAudioBufferSampleSize;
    private int mAudioBufferSize;
    private boolean isRecord=false;
    private String rawFileName;
    private String fileName;
    private AudioRecord audioRecord;
    private ExecutorService executorService;
    private DisposeFailureListener mDisposeFailureListener;
    private static AudioRecordUtil audioRecordUtil;
    public static synchronized AudioRecordUtil getInstance(){
        if(audioRecordUtil==null){
            audioRecordUtil=new AudioRecordUtil();
        }
        return audioRecordUtil;
    }
    private AudioRecordUtil(){
       executorService= Executors.newSingleThreadExecutor();
    }
    private synchronized  void initAudioRecord(){
        try{
            int sampleRate=16000;
            int channelConfig= AudioFormat.CHANNEL_IN_MONO;
            int audioFormat=AudioFormat.ENCODING_PCM_16BIT;
            mAudioBufferSampleSize= AudioRecord.getMinBufferSize(sampleRate,channelConfig,audioFormat);
            mAudioBufferSize=mAudioBufferSampleSize;
            audioRecord=new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,mAudioBufferSize);
            int audioRecordState=audioRecord.getState();
            if(audioRecordState!=AudioRecord.STATE_INITIALIZED){
                mDisposeFailureListener.disposeFailure();
            }
        }catch (IllegalFormatException e){
            Log.i("aaa",e.toString());
            e.printStackTrace();
        }
    }
    public void startRecord(){
        initAudioRecord();
        audioRecord.startRecording();
        isRecord=true;
        executorService.execute(new AudioRecordThread());
    }
    public void closeRecord() {
        if (audioRecord != null) {
            isRecord = false;//停止文件写入
            audioRecord.stop();
            audioRecord.release();//释放资源
            audioRecord = null;
        }
    }
    private class AudioRecordThread implements Runnable{
        @Override
        public void run() {
            writeDataToFile();
            copyWaveFile(rawFileName,fileName,mAudioBufferSampleSize);
            File f=new File(rawFileName);
            if(f.exists())
                f.delete();
        }
    }
    private  void writeDataToFile(){
        String path= Environment.getExternalStorageDirectory().getAbsolutePath();
        File dir=new File(path+"/historyApp");
        if(!dir.exists())
            dir.mkdirs();
        UUID uuid=UUID.randomUUID();
        rawFileName=dir.getAbsolutePath()+File.separator+uuid.toString().substring(4,7)+AUDIO_RECORD_RAW_FILE;
        fileName=dir.getAbsolutePath()+File.separator+uuid.toString().substring(4,7)+AUDIO_RECORD_FILE;
        File file=new File(rawFileName);
        byte[] audioData=new byte[mAudioBufferSampleSize];
        FileOutputStream fos=null;
        int readSize=0;
        try{
            fos=new FileOutputStream(file);
        }catch(IOException e){e.printStackTrace();}
        while(isRecord==true){
            readSize=audioRecord.read(audioData,0,mAudioBufferSampleSize);
            if(AudioRecord.ERROR_INVALID_OPERATION!=readSize && fos!=null){
                try {
                    fos.write(audioData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            if(fos!=null){
                fos.close();
            }
        }catch(IOException e){e.printStackTrace();}
    }
    public  void copyWaveFile(String inFilename,String outFilename,int sampleSize){
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = AUDIO_SAMPLE_RATE;
        int channels = 1;
        long byteRate = 16 * AUDIO_SAMPLE_RATE * channels / 8;
        byte[] data = new byte[sampleSize];
        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);
            while (in.read(data) != -1) {
                out.write(data);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里提供一个头信息。插入这些信息就可以得到可以播放的文件。
     * 为我为啥插入这44个字节，这个还真没深入研究，不过你随便打开一个wav
     * 音频的文件，可以发现前面的头文件可以说基本一样哦。每种格式的文件都有
     * 自己特有的头文件。
     */
    private  void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,
                                     long totalDataLen, long longSampleRate, int channels, long byteRate)
            throws IOException {
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1; // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8); // block align
        header[33] = 0;
        header[34] = 16; // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }
    public String getFileName(){return fileName;}
    public void deleteFile(){
        if(fileName!=null) {
            File file = new File(fileName);
            if (file.exists())
                file.delete();
        }
    }
    public interface DisposeFailureListener{
         void disposeFailure();
    }
    public void setDisposeFailureListener(DisposeFailureListener disposeFailureListener){
        mDisposeFailureListener=disposeFailureListener;
    }
}
