package com.peftif.android.Perfect_fit.FloatingMiniPerfit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.peftif.android.Perfect_fit.FinalActivity;
import com.peftif.android.Perfect_fit.GlobalData;
import com.peftif.android.Perfect_fit.MainActivity;
import com.peftif.android.Perfect_fit.R;


public class FloatingWidgetService extends Service implements View.OnClickListener {
    private WindowManager mWindowManager;
    private View mFloatingWidgetView, collapsedView, expandedView;
    private ImageView remove_image_view;
    private Point szWindow = new Point();
    private View removeFloatingWidgetView;
    private LinearLayout linear2edt;
    private TextView txt_title;
    private LinearLayout linear1table;
    static InputMethodManager ime = null;
    public static Context mContext;
    int selected_state = -1;


    private int x_init_cord, y_init_cord, x_init_margin, y_init_margin;

    //Variable to check if the Floating widget view is on left side or in right side
    // initially we are displaying Floating widget view to Left side so set it to true
    private boolean isLeft = true;

    public FloatingWidgetService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //init WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        getWindowManagerDefaultDisplay();
        GlobalData.isWidgetDistroyed = false;
        mContext = this;


        //Init LayoutInflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        addRemoveView(inflater);
        addFloatingWidgetView(inflater);
        implementClickListeners();
        implementTouchListenerToFloatingWidgetView();

        final TextView txt_table1 = mFloatingWidgetView.findViewById(R.id.txt_table1);
        final TextView txt_table2 = mFloatingWidgetView.findViewById(R.id.txt_table2);
        final TextView txt_table3 = mFloatingWidgetView.findViewById(R.id.txt_table3);
        final TextView txt_table4 = mFloatingWidgetView.findViewById(R.id.txt_table4);
        txt_title = mFloatingWidgetView.findViewById(R.id.title);

        final EditText edt_table1 = mFloatingWidgetView.findViewById(R.id.edt_table1);
        final EditText edt_table2 = mFloatingWidgetView.findViewById(R.id.edt_table2);
        final EditText edt_table3 = mFloatingWidgetView.findViewById(R.id.edt_table3);
        final EditText edt_table4 = mFloatingWidgetView.findViewById(R.id.edt_table4);

        final Button btn_tshirt = mFloatingWidgetView.findViewById(R.id.btn_tshirt);
        final Button btn_skirt = mFloatingWidgetView.findViewById(R.id.chima);
        final Button btn_pants = mFloatingWidgetView.findViewById(R.id.btn_pant);
        final Button btn_dress = mFloatingWidgetView.findViewById(R.id.btn_onepiece);
        final Button btn_finish = mFloatingWidgetView.findViewById(R.id.btn_finish);
        linear1table = mFloatingWidgetView.findViewById(R.id.linear1table);
        linear2edt= mFloatingWidgetView.findViewById(R.id.linear2edt);
        linear1table.setVisibility(View.VISIBLE);
        linear2edt.setVisibility(View.GONE);
        txt_title.setText("옷의 종류를 골라주세요");

        edt_table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edt_table1.requestFocus();
                //키보드 보이기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                imm.showSoftInput(edt_table1, 0);

            }
        });

        edt_table2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_table2.requestFocus();
                //키보드 보이기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                imm.showSoftInput(edt_table2, 0);
            }
        });

        edt_table3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_table3.requestFocus();
                //키보드 보이기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                imm.showSoftInput(edt_table3, 0);

            }
        });
        edt_table4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_table4.requestFocus();
                //키보드 보이기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                imm.showSoftInput(edt_table4 ,0);
            }
        });


        btn_tshirt.setOnClickListener(view->{
            txt_table1.setText("어깨 폭");
            txt_table2.setText("팔 길이");
            txt_table3.setText("밑단 폭");
            txt_table4.setText("총 길이");
            txt_table1.setVisibility(View.VISIBLE);
            txt_table2.setVisibility(View.VISIBLE);
            txt_table3.setVisibility(View.VISIBLE);
            txt_table4.setVisibility(View.VISIBLE);
            linear1table.setVisibility(View.GONE);
            linear2edt.setVisibility(View.VISIBLE);
            txt_title.setText("치수표의 치수를 입력해주세요");

            selected_state = 1;

        });
        btn_pants.setOnClickListener(view->{
            linear1table.setVisibility(View.GONE);
            linear2edt.setVisibility(View.VISIBLE);
            txt_table1.setText("허리단면");
            txt_table2.setText("총 기장");
            txt_table1.setVisibility(View.VISIBLE);
            txt_table2.setVisibility(View.VISIBLE);
            txt_table3.setVisibility(View.GONE);
            txt_table4.setVisibility(View.GONE);
            txt_title.setText("치수표의 치수를 입력해주세요");

            selected_state = 2;


        });
        btn_dress.setOnClickListener(view->{ //어깨 허리 소매 총장 밑단
            txt_table1.setText("어깨 폭");
            txt_table2.setText("팔 길이");
            txt_table3.setText("밑단 폭");
            txt_table4.setText("총 길이");
            txt_table1.setVisibility(View.VISIBLE);
            txt_table2.setVisibility(View.VISIBLE);
            txt_table3.setVisibility(View.VISIBLE);
            txt_table4.setVisibility(View.VISIBLE);
            linear1table.setVisibility(View.VISIBLE);
            linear2edt.setVisibility(View.VISIBLE);
            txt_title.setText("치수표의 치수를 입력해주세요");

            selected_state = 3;

        });

        btn_skirt.setOnClickListener(view->{//밑단 총길이 허리
            txt_table1.setText("어깨 폭");
            txt_table2.setText("팔 길이");
            txt_table3.setText("밑단 폭");
            txt_table4.setText("총 길이");
            txt_table1.setVisibility(View.VISIBLE);
            txt_table2.setVisibility(View.VISIBLE);
            txt_table3.setVisibility(View.VISIBLE);
            txt_table4.setVisibility(View.VISIBLE);
            linear1table.setVisibility(View.VISIBLE);
            linear2edt.setVisibility(View.VISIBLE);
            txt_title.setText("치수표의 치수를 입력해주세요");

            selected_state = 4;

        });


        //TODO 키보드
        ime = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        ime.showSoftInput(edit, 0);
        ime.showSoftInputFromInputMethod(edt_table1.getWindowToken(), InputMethodManager.SHOW_FORCED);
        //TODO


        btn_finish.setOnClickListener(view ->{
            if(linear2edt.getVisibility() == View.VISIBLE ){
                if (edt_table1.getText().toString().equals("")||edt_table2.getText().toString().equals("")||edt_table3.getText().toString().equals("")||edt_table4.getText().toString().equals("")){
                    Toast.makeText(FloatingWidgetService.this, "주어진 치수표의 치수를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{


                    switch (selected_state){
                        case 1:
                            //티셔츠
                            Intent mintent = new Intent(FloatingWidgetService.this, FinalActivity.class);
                            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mintent.putExtra("arm", Integer.parseInt(edt_table1.getText().toString()));
                            mintent.putExtra("shoulder", Integer.parseInt(edt_table2.getText().toString()));
                            startActivity(mintent);
//                            mintent.putExtra("verticalWidth", edt_table4.getText().toString());
//                            mintent.putExtra("horizontalWidth", edt_table3.getText().toString());
                            break;
                        case 2:
                            //바지
                            Toast.makeText(getApplicationContext(), "바지 가상피팅 기능은 곧 업데이트 될 예정입니다!", Toast.LENGTH_SHORT).show();
//                            mintent.putExtra("armDistance1", edt_table1.getText().toString());
//                            mintent.putExtra("shoulderWidth", edt_table2.getText().toString());
//                            mintent.putExtra("verticalWidth", edt_table4.getText().toString());
//                            mintent.putExtra("horizontalWidth", edt_table3.getText().toString());

                        case 3:
                            //드레스
                            Toast.makeText(getApplicationContext(), "드레스 및 원피스 가상피팅 기능은 곧 업데이트 될 예정입니다!", Toast.LENGTH_SHORT).show();
//                            mintent.putExtra("armDistance1", edt_table1.getText().toString());
//                            mintent.putExtra("shoulderWidth", edt_table2.getText().toString());
//                            mintent.putExtra("verticalWidth", edt_table4.getText().toString());
//                            mintent.putExtra("horizontalWidth", edt_table3.getText().toString());
                        case 4:
//                            //치마
//                            mintent.putExtra("armDistance1", edt_table1.getText().toString());
                            Toast.makeText(getApplicationContext(), "치마 가상피팅 기능은 곧 업데이트 될 예정입니다!", Toast.LENGTH_SHORT).show();
//                            mintent.putExtra("shoulderWidth", edt_table2.getText().toString());
//                            mintent.putExtra("verticalWidth", edt_table4.getText().toString());
//                            mintent.putExtra("horizontalWidth", edt_table3.getText().toString());
                    }



                }

            }
        });


        }

        public void stopWidget(){
         stopSelf();
         GlobalData.isWidgetDistroyed = true;
        }



    /*  Add Remove View to Window Manager  */
    private View addRemoveView(LayoutInflater inflater) {
        //Inflate the removing view layout we created
        removeFloatingWidgetView = inflater.inflate(R.layout.remove_floating_widget, null);

        //Add the view to the window.
        WindowManager.LayoutParams paramRemove = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                 WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        paramRemove.gravity = Gravity.TOP | Gravity.LEFT;

        //Initially the Removing widget view is not visible, so set visibility to GONE
        removeFloatingWidgetView.setVisibility(View.GONE);
        remove_image_view = (ImageView) removeFloatingWidgetView.findViewById(R.id.remove_img);

        //Add the view to the window
        mWindowManager.addView(removeFloatingWidgetView, paramRemove);
        return remove_image_view;
    }

    /*  Add Floating Widget View to Window Manager  */
    private void addFloatingWidgetView(LayoutInflater inflater) {
        //Inflate the floating view layout we created
        mFloatingWidgetView = inflater.inflate(R.layout.floating_widget, null);

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);



        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;

        //Initially view will be added to top-left corner, you change x-y coordinates according to your need
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager.addView(mFloatingWidgetView, params);

        //find id of collapsed view layout
        collapsedView = mFloatingWidgetView.findViewById(R.id.collapse_view);

        //find id of the expanded view layout
        expandedView = mFloatingWidgetView.findViewById(R.id.expanded_container);
    }

    private void getWindowManagerDefaultDisplay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            mWindowManager.getDefaultDisplay().getSize(szWindow);
        else {
            int w = mWindowManager.getDefaultDisplay().getWidth();
            int h = mWindowManager.getDefaultDisplay().getHeight();
            szWindow.set(w, h);
        }
    }

    /*  Implement Touch Listener to Floating Widget Root View  */
    private void implementTouchListenerToFloatingWidgetView() {
        //Drag and move floating view using user's touch action.
        mFloatingWidgetView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {

            long time_start = 0, time_end = 0;

            boolean isLongClick = false;//variable to judge if user click long press
            boolean inBounded = false;//variable to judge if floating view is bounded to remove view
            int remove_img_width = 0, remove_img_height = 0;

            Handler handler_longClick = new Handler();
            Runnable runnable_longClick = new Runnable() {
                @Override
                public void run() {
                    //On Floating Widget Long Click

                    //Set isLongClick as true
                    isLongClick = true;

                    //Set remove widget view visibility to VISIBLE
                    removeFloatingWidgetView.setVisibility(View.VISIBLE);

                    onFloatingWidgetLongClick();
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Get Floating widget view params
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

                //get the touch location coordinates
                int x_cord = (int) event.getRawX();
                int y_cord = (int) event.getRawY();

                int x_cord_Destination, y_cord_Destination;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        time_start = System.currentTimeMillis();

                        handler_longClick.postDelayed(runnable_longClick, 600);

                        remove_img_width = remove_image_view.getLayoutParams().width;
                        remove_img_height = remove_image_view.getLayoutParams().height;

                        x_init_cord = x_cord;
                        y_init_cord = y_cord;

                        //remember the initial position.
                        x_init_margin = layoutParams.x;
                        y_init_margin = layoutParams.y;

                        return true;
                    case MotionEvent.ACTION_UP:
                        isLongClick = false;
                        removeFloatingWidgetView.setVisibility(View.GONE);
                        remove_image_view.getLayoutParams().height = remove_img_height;
                        remove_image_view.getLayoutParams().width = remove_img_width;
                        handler_longClick.removeCallbacks(runnable_longClick);

                        //If user drag and drop the floating widget view into remove view then stop the service
                        if (inBounded) {
                            stopSelf();
                            GlobalData.isWidgetDistroyed = true;
                            inBounded = false;
                            break;
                        }


                        //Get the difference between initial coordinate and current coordinate
                        int x_diff = x_cord - x_init_cord;
                        int y_diff = y_cord - y_init_cord;

                        //The check for x_diff <5 && y_diff< 5 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Math.abs(x_diff) < 5 && Math.abs(y_diff) < 5) {
                            time_end = System.currentTimeMillis();

                            //Also check the difference between start time and end time should be less than 300ms
                            if ((time_end - time_start) < 300)
                                onFloatingWidgetClick();

                        }

                        y_cord_Destination = y_init_margin + y_diff;

                        int barHeight = getStatusBarHeight();
                        if (y_cord_Destination < 0) {
                            y_cord_Destination = 0;
                        } else if (y_cord_Destination + (mFloatingWidgetView.getHeight() + barHeight) > szWindow.y) {
                            y_cord_Destination = szWindow.y - (mFloatingWidgetView.getHeight() + barHeight);
                        }

                        layoutParams.y = y_cord_Destination;

                        inBounded = false;

                        //reset position if user drags the floating view
                        resetPosition(x_cord);

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int x_diff_move = x_cord - x_init_cord;
                        int y_diff_move = y_cord - y_init_cord;

                        x_cord_Destination = x_init_margin + x_diff_move;
                        y_cord_Destination = y_init_margin + y_diff_move;

                        //If user long click the floating view, update remove view
                        if (isLongClick) {
                            int x_bound_left = szWindow.x / 2 - (int) (remove_img_width * 2);
                            int x_bound_right = szWindow.x / 2 + (int) (remove_img_width * 2);
                            int y_bound_top = szWindow.y - (int) (remove_img_height * 2);

                            //If Floating view comes under Remove View update Window Manager
                            if ((x_cord >= x_bound_left && x_cord <= x_bound_right) && y_cord >= y_bound_top) {
                                inBounded = true;

                                int x_cord_remove = (int) ((szWindow.x - (remove_img_height * 1.5)) / 2);
                                int y_cord_remove = (int) (szWindow.y - ((remove_img_width * 1.5) + getStatusBarHeight()));

                                if (remove_image_view.getLayoutParams().height == remove_img_height) {
                                    remove_image_view.getLayoutParams().height = (int) (remove_img_height * 1.5);
                                    remove_image_view.getLayoutParams().width = (int) (remove_img_width * 1.5);

                                    WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeFloatingWidgetView.getLayoutParams();
                                    param_remove.x = x_cord_remove;
                                    param_remove.y = y_cord_remove;

                                    mWindowManager.updateViewLayout(removeFloatingWidgetView, param_remove);
                                }

                                layoutParams.x = x_cord_remove + (Math.abs(removeFloatingWidgetView.getWidth() - mFloatingWidgetView.getWidth())) / 2;
                                layoutParams.y = y_cord_remove + (Math.abs(removeFloatingWidgetView.getHeight() - mFloatingWidgetView.getHeight())) / 2;

                                //Update the layout with new X & Y coordinate
                                mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
                                break;
                            } else {
                                //If Floating window gets out of the Remove view update Remove view again
                                inBounded = false;
                                remove_image_view.getLayoutParams().height = remove_img_height;
                                remove_image_view.getLayoutParams().width = remove_img_width;
                                //onFloatingWidgetClick();
                            }

                        }


                        layoutParams.x = x_cord_Destination;
                        layoutParams.y = y_cord_Destination;

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
                        return true;
                }
                return false;
            }
        });
    }

    private void implementClickListeners() {
        mFloatingWidgetView.findViewById(R.id.close_floating_view).setOnClickListener(this);
        mFloatingWidgetView.findViewById(R.id.close_expanded_view).setOnClickListener(this);
        mFloatingWidgetView.findViewById(R.id.open_activity_button).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_floating_view:
                //close the service and remove the from from the window
                linear1table.setVisibility(View.VISIBLE);
                linear2edt.setVisibility(View.GONE);
                txt_title.setText("옷의 종류를 골라주세요");
                stopSelf();
                GlobalData.isWidgetDistroyed = true;
                break;
            case R.id.close_expanded_view:
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);

                linear1table.setVisibility(View.VISIBLE);
                linear2edt.setVisibility(View.GONE);
                txt_title.setText("옷의 종류를 골라주세요");
                break;
            case R.id.open_activity_button:
                linear1table.setVisibility(View.VISIBLE);
                linear2edt.setVisibility(View.GONE);
                txt_title.setText("옷의 종류를 골라주세요");
                //open the activity and stop service
                Intent intent = new Intent(FloatingWidgetService.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                //close the service and remove view from the view hierarchy
                stopSelf();
                GlobalData.isWidgetDistroyed = true;
                break;
        }
    }

    /*  on Floating Widget Long Click, increase the size of remove view as it look like taking focus */
    private void onFloatingWidgetLongClick() {
        //Get remove Floating view params
        WindowManager.LayoutParams removeParams = (WindowManager.LayoutParams) removeFloatingWidgetView.getLayoutParams();

        //get x and y coordinates of remove view
        int x_cord = (szWindow.x - removeFloatingWidgetView.getWidth()) / 2;
        int y_cord = szWindow.y - (removeFloatingWidgetView.getHeight() + getStatusBarHeight());


        removeParams.x = x_cord;
        removeParams.y = y_cord;

        //Update Remove view params
        mWindowManager.updateViewLayout(removeFloatingWidgetView, removeParams);
    }

    /*  Reset position of Floating Widget view on dragging  */
    private void resetPosition(int x_cord_now) {
        if (x_cord_now <= szWindow.x / 2) {
            isLeft = true;
            moveToLeft(x_cord_now);
        } else {
            isLeft = false;
            moveToRight(x_cord_now);
        }

    }


    /*  Method to move the Floating widget view to Left  */
    private void moveToLeft(final int current_x_cord) {
        final int x = szWindow.x - current_x_cord;

        new CountDownTimer(500, 5) {
            //get params of Floating Widget view
            WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

            public void onTick(long t) {
                long step = (500 - t) / 5;

                mParams.x = 0 - (int) (current_x_cord * current_x_cord * step);

                //If you want bounce effect uncomment below line and comment above line
                 mParams.x = 0 - (int) (double) bounceValue(step, x)-50;


                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }

            public void onFinish() {
                mParams.x = 0;

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }
        }.start();
    }

    /*  Method to move the Floating widget view to Right  */
    private void moveToRight(final int current_x_cord) {

        new CountDownTimer(500, 5) {
            //get params of Floating Widget view
            WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

            public void onTick(long t) {
                long step = (500 - t) / 5;

                mParams.x = (int) (szWindow.x + (current_x_cord * current_x_cord * step) - mFloatingWidgetView.getWidth());

               // If you want bounce effect uncomment below line and comment above line
                  mParams.x = szWindow.x + (int) (double) bounceValue(step, current_x_cord) - mFloatingWidgetView.getWidth()+50;

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }

            public void onFinish() {
                mParams.x = szWindow.x - mFloatingWidgetView.getWidth();

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }
        }.start();
    }

    /*  Get Bounce value if you want to make bounce effect to your Floating Widget */
    private double bounceValue(long step, long scale) {
        double value = scale * java.lang.Math.exp(-0.055 * step) * java.lang.Math.cos(0.08 * step);
        return value;
    }


    /*  Detect if the floating view is collapsed or expanded */
    private boolean isViewCollapsed() {
        return mFloatingWidgetView == null || mFloatingWidgetView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }


    /*  return status bar height on basis of device display metrics  */
    private int getStatusBarHeight() {
        return (int) Math.ceil(25 * getApplicationContext().getResources().getDisplayMetrics().density);
    }


    /*  Update Floating Widget view coordinates on Configuration change  */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        getWindowManagerDefaultDisplay();

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {


            if (layoutParams.y + (mFloatingWidgetView.getHeight() + getStatusBarHeight()) > szWindow.y) {
                layoutParams.y = szWindow.y - (mFloatingWidgetView.getHeight() + getStatusBarHeight());
                mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
            }

            if (layoutParams.x != 0 && layoutParams.x < szWindow.x) {
                resetPosition(szWindow.x);
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (layoutParams.x > szWindow.x) {
                resetPosition(szWindow.x);
            }

        }

    }

    /*  on Floating widget click show expanded view  */
    private void onFloatingWidgetClick() {
        if (isViewCollapsed()) {
            //When user clicks on the image view of the collapsed layout,
            //visibility of the collapsed layout will be changed to "View.GONE"
            //and expanded view will become visible.
            collapsedView.setVisibility(View.GONE);
            expandedView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*  on destroy remove both view from window manager */

        if (mFloatingWidgetView != null)
            mWindowManager.removeView(mFloatingWidgetView);

        if (removeFloatingWidgetView != null)
            mWindowManager.removeView(removeFloatingWidgetView);

    }


}