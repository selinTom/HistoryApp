package com.example.devov.historyapp.utils.common;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by linjizong on 15/6/11.
 */
public class LocalImageHelper {
    private static LocalImageHelper instance;

    public static LocalImageHelper getInstance() {
        return instance;
    }

    private final Context context;

    //大图遍历字段
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.ORIENTATION
    };
    //小图遍历字段
    private static final String[] THUMBNAIL_STORE_IMAGE = {
            MediaStore.Images.Thumbnails._ID,
            MediaStore.Images.Thumbnails.DATA
    };

    final ArrayList<LocalFile> paths = new ArrayList<>();
    final ArrayList<String> folderNames = new ArrayList<>();
    final Map<String, ArrayList<LocalFile>> folders = new HashMap<>();

    public static void init(Context context) {
        instance = new LocalImageHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.initImage();
            }
        }).start();
    }

    private LocalImageHelper(Context context) {
        this.context = context;
    }

    public Map<String, ArrayList<LocalFile>> getFolderMap() {
        return folders;
    }

    public boolean isInited() {
        return paths.size() > 0;
    }

    private boolean isRunning = false;

    public boolean isInitFinished() {
        return isInitFinished;
    }

    private boolean isInitFinished = false;

    public synchronized void initImage() {
        if (isRunning)
            return;
        isRunning=true;
        if (isInited()){
//            paths.clear();
//            folderNames.clear();
//            folders.clear();
            return;
        }
        //获取大图的游标
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  // 大图URI
                STORE_IMAGES,   // 字段
                null,         // No where clause
                null,         // No where clause
                MediaStore.Images.Media.DATE_TAKEN + " DESC"); //根据时间升序
        if (cursor == null)
            return;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);//大图ID
            String path = cursor.getString(1);//大图路径
            if (path == null){
                path = "";
            }
            File file = new File(path);
            //判断大图是否存在
            if (file.exists()) {
                //小图URI
                String thumbUri = getThumbnail(id, path);
                //获取大图URI
                String uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().
                        appendPath(Integer.toString(id)).build().toString();
                if(StringUtils.isEmpty(uri))
                    continue;
                if (StringUtils.isEmpty(thumbUri))
                    thumbUri = uri;
                //获取目录名
                String folder = file.getParentFile().getName();

                LocalFile localFile = new LocalFile();
                localFile.setImagePath(path);
                localFile.setOriginalUri(uri);
                localFile.setThumbnailUri(thumbUri);
                int degree = cursor.getInt(2);
                if (degree != 0) {
                    degree = degree + 180;
                }
                localFile.setOrientation(360-degree);

                //判断文件夹是否已经存在
                if (!folder.equals("haohaozhu")){
                    paths.add(localFile);
                    if (folders.containsKey(folder)) {
                        folders.get(folder).add(localFile);
                    } else {
                        ArrayList<LocalFile> files = new ArrayList<>();
                        files.add(localFile);
                        folders.put(folder, files);
                    }
                }
            }
        }
        folders.put("所有图片", paths);
        cursor.close();
        folderNames.clear();
        Iterator iter = folders.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            folderNames.add(key);
        }
        //根据文件夹内的图片数量降序显示
        Collections.sort(folderNames, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                Integer num1 = getFolder(arg0).size();
                Integer num2 = getFolder(arg1).size();
                return num2.compareTo(num1);
            }
        });
        isRunning=false;
        isInitFinished = true;
    }

    public ArrayList<String> getFolderNames(){
        return folderNames;
    }

    private String getThumbnail(int id, String path) {
        //获取大图的缩略图
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                    THUMBNAIL_STORE_IMAGE,
                    MediaStore.Images.Thumbnails.IMAGE_ID + " = ?",
                    new String[]{id + ""},
                    null);
            if(cursor!=null){
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int thumId = cursor.getInt(0);
                    String uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI.buildUpon().
                            appendPath(Integer.toString(thumId)).build().toString();
                    cursor.close();
                    return uri;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return null;
    }

    public ArrayList<LocalFile> getFolder(String folder) {
        return folders.get(folder);
    }
    public  void restart(){
        isInitFinished=false;
        isRunning=false;
        paths.clear();
        folderNames.clear();
        folders.clear();
        initImage();
    }

    public static class LocalFile implements Serializable {
        private String originalUri;//原图URI
        private String thumbnailUri;//缩略图URI
        private int orientation;//图片旋转角度
        private String imagePath;//图片旋转角度
        private String parentFolder;//图片旋转角度

        public String getImagePath() {
            return imagePath;
        }

        public String getParentFolder() {
            return parentFolder;
        }

        public void setParentFolder(String parentFolder) {
            this.parentFolder = parentFolder;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getThumbnailUri() {
            return thumbnailUri;
        }

        public void setThumbnailUri(String thumbnailUri) {
            this.thumbnailUri = thumbnailUri;
        }

        public String getOriginalUri() {
            return originalUri;
        }

        public void setOriginalUri(String originalUri) {
            this.originalUri = originalUri;
        }


        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int exifOrientation) {
            orientation =  exifOrientation;
        }

    }

    public synchronized void addNewPhoto(LocalImageHelper.LocalFile localFile){
        if (!TextUtils.equals(folders.get("所有图片").get(0).getImagePath(), localFile.getImagePath())){
            folders.get("所有图片").add(0, localFile);
        }
        if (folders.get(localFile.getParentFolder()) != null) {
            if (!TextUtils.equals(folders.get(localFile.getParentFolder()).get(0).getImagePath(), localFile.getImagePath())){
                folders.get(localFile.getParentFolder()).add(0, localFile);
            }
        } else {
            ArrayList<LocalFile> newLocalFiles = new ArrayList<LocalFile>();
            newLocalFiles.add(localFile);
            folders.put(localFile.getParentFolder(), newLocalFiles);
            folderNames.add(localFile.getParentFolder());
        }
    }
}
