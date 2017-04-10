package com.example.devov.historyapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.devov.historyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.devov.historyapp.utils.xUtilsHelper.displayImage;

/**
 * Created by devov on 2017/3/23.
 */

public class T4Activity extends Activity {
    @BindView(R.id.iv)
    ImageView view;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    int i;
    String url="http://img.hhz1.cn/App-imageShow/o_nphone/2c1/6341620ku08d00000o2zzol?iv=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_4);
        ButterKnife.bind(this);
        displayImage(view,url);
        fab.setOnClickListener(v->{
            View rootView=getWindow().getDecorView();
            Log.i("aaa",rootView.getClass().getName());
        });
        fab.postDelayed(()->{
            i=7;
            Log.i("aaa","BBB"+i);
        },1000);
        Log.i("aaa","AAA"+i);

    }
}

//public class ArticleDetailsFragment extends BaseLifeCycleSupportFragment {
//    @BindView(R.id.coordinatorLayout)
//    CoordinatorLayout coordinatorLayout;
//    @BindView(R.id.coolapsing_toolbar_layout)
//    CollapsingToolbarLayout collapsingToolbarLayout;
//    @BindView(R.id.cover_image_view)
//    SimpleDraweeView imageView;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    private String articleId;
//    private boolean isPreview;
//
//    //////////////////////////////////////////////////////////////
//    private String url = "http://img.hhz1.cn/App-imageShow/o_nphone/743/db14722bc15o00000ojntth?iv=1";
//    private String title = "人间烟火气--被心爱之物包裹的家";
//    /////////////////////////////////////////////////////////////////
//
//    public static ArticleDetailsFragment getInstance(String articleId, boolean isPreview) {
//        ArticleDetailsFragment articleDetailsFragment = new ArticleDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString("article_id", articleId);
//        args.putBoolean("is_preview", isPreview);
//        articleDetailsFragment.setArguments(args);
//        return articleDetailsFragment;
//    }
//
//    @Override
//    protected int getFragmentLayout() {
//        return R.layout.activity_article_details_new;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        articleId = bundle.getString("article_id");
//        isPreview = bundle.getBoolean("is_preview");
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Fresco.getImagePipeline().clearMemoryCaches();
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(v -> getActivity().finish());
//        initTitleBar();
//
//    }
//
//    private void initTitleBar() {
//        Postprocessor redMeshPostprocessor = new BasePostprocessor() {
//            @Override
//            public String getName() {
//                return "redMeshPostprocessor";
//            }
//
//            @Override
//            public void process(Bitmap bitmap) {
//                collapsingToolbarLayout.setTitle(title);
//                Palette.Builder builder=Palette.from(bitmap);
//                builder.generate(palette -> {
//                    Palette.Swatch swatch=palette.getVibrantSwatch();
//                    if(swatch!=null)collapsingToolbarLayout.setContentScrimColor(swatch.getRgb());
//                    else collapsingToolbarLayout.setContentScrimColor(0x000000);
//                    collapsingToolbarLayout.invalidate();
//                });
//            }
//        };
//
//        ImageRequest imageRequest = ImageRequestBuilder
//                .newBuilderWithSource(Uri.parse(url))
//                .setPostprocessor(redMeshPostprocessor)
//                .build();
//        DraweeController draweeController=Fresco.newDraweeControllerBuilder()
//                .setImageRequest(imageRequest)
//                .build();
//        imageView.setController(draweeController);
//    }
//}