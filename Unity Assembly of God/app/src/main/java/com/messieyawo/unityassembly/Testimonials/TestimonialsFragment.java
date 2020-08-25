package com.messieyawo.unityassembly.Testimonials;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.messieyawo.unityassembly.EvntManager.EventFirebaseDatabaseHelper;
import com.messieyawo.unityassembly.EvntManager.EventObject;
import com.messieyawo.unityassembly.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestimonialsFragment extends Fragment {
     private RecyclerView mRecyclerView;
     ProgressBar progressBar;
     FloatingActionButtonExpandable btFloat;
    private EditText mName,mTitle,mDate,mDescription,mLocation;


    public TestimonialsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_testimonials, container, false);

        btFloat = view.findViewById(R.id.bt_float);
        mRecyclerView = view.findViewById(R.id.recyclerViewTestimony);
        progressBar = view.findViewById(R.id.loading_testimony_main);



        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling up
                } else {
                    // Scrolling down
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                    btFloat.expand(true);
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                    btFloat.expand(true);
                } else {
                    // Do something
                    btFloat.collapse(true);
                }
            }
        });


//
//        mRecyclerView.setOnScrollChangeListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int lastItem = firstVisibleItem +visibleItemCount;
//            if (lastItem == totalItemCount){
//
//                btFloat.expand(true);
//            }else{
//                btFloat.collapse(true);
//            }
//            }
//        });

        //        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            int lastItem = firstVisibleItem +visibleItemCount;
//            if (lastItem == totalItemCount){
//
//                btFloat.expand(true);
//            }else{
//                btFloat.collapse(true);
//            }
//            }
//        });









      new TestiMonialsFirebaseDatabaseHelper().readTestimonials(new TestiMonialsFirebaseDatabaseHelper.DataStatus() {
          @Override
          public void DataIsLoaded(List<Testimonials> testimonials, List<String> keys) {
              progressBar.setVisibility(View.GONE);
              new TestimonialsRecyclerViewConfig().setConfg(mRecyclerView,getActivity(),testimonials,keys);
          }

          @Override
          public void DataInserted() {

          }

          @Override
          public void DataDeleted() {

          }

          @Override
          public void DataUpdated() {

          }
        });

        btFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder(getActivity())
                        .setTitle("Post Testimony")
                        .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                        .setMessage("Please this is a church app not to serve our own purposes but for only God.So please read " +
                                "and understand the policies that rules posting testimonies here as indicated buy the pastor.Your testimony will be seen everywhere " +
                                "on phone having this app installed.")
                        .setNegativeBtnText("Cancel")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                        .setPositiveBtnText("I agree")
                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.drawable.testimony_48, Icon.Visible)
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {

                                Dialog d = new Dialog(getActivity());
                                d.setContentView(R.layout.input_dialog_testimony);
                                mName =  d.findViewById(R.id.testimonyName);
                                mTitle =  d.findViewById(R.id.titleTestimony);
                                mDescription =  d.findViewById(R.id.testmonyDescripton);
                                mDate = d.findViewById(R.id.dateTestimony);
                                mLocation = d.findViewById(R.id.locatonTestimony);


                               String mName_a = mName.getText().toString().trim();
                               String mTitle_a = mTitle.getText().toString().trim();
                               String mDescription_a = mDescription.getText().toString().trim();
                               String mLocation_a = mLocation.getText().toString().trim();
                               String mDate_a = mDate.getText().toString().trim();




                                Button saveBtnTestimony =  d.findViewById(R.id.saveBtnTestimony);
                                Button cansel_btn = d.findViewById(R.id.dismiss_dialog);
                                cansel_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.dismiss();

                                    }
                                });

                                //SAVE
                                saveBtnTestimony.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //GET DATA
                                        Testimonials testimonials = new Testimonials();
                                        testimonials.setDate(mDate_a);
                                       testimonials.setDescription(mDescription_a);
                                        testimonials.setName(mName_a);
                                        testimonials.setTitle(mTitle_a);
                                        testimonials.setLocation(mLocation_a);



                                       new TestiMonialsFirebaseDatabaseHelper().addTestimonials(testimonials, new TestiMonialsFirebaseDatabaseHelper.DataStatus() {
                                           @Override
                                           public void DataIsLoaded(List<Testimonials> testimonials, List<String> keys) {

                                           }

                                           @Override
                                           public void DataInserted() {
                                               d.dismiss();
                                               DynamicToast.makeSuccess(getActivity(), "Your post has been posted successfully..", Toast.LENGTH_SHORT).show();

                                           }

                                           @Override
                                           public void DataDeleted() {

                                           }

                                           @Override
                                           public void DataUpdated() {

                                           }
                                       });


                                    }
                                });

                                d.show();
                                Window window = d.getWindow();
                                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            }
                        })
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {


                                Toast.makeText(getContext(),"Cancel",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
            }
        });



//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            int lastItem = firstVisibleItem +visibleItemCount;
//            if (lastItem == totalItemCount){
//
//                btFloat.expand(true);
//            }else{
//                btFloat.collapse(true);
//            }
//            }
//        });

        return view;
    }
}
