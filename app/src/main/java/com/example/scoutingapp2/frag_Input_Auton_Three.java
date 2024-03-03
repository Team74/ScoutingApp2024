package com.example.scoutingapp2;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_Input_Auton_Three#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Input_Auton_Three extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_Input_Auton_Three() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_Input_Auton_One.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_Input_Auton_Three newInstance(String param1, String param2) {
        frag_Input_Auton_Three fragment = new frag_Input_Auton_Three();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    View pm_Speaker1, pm_Amp1, pm_Grab1, pm_Speaker2, pm_Amp2, pm_Grab2, pm_Speaker3, pm_Amp3, pm_Grab3; //The plus or minus. pm = +-
    TextView tv_Speaker1, tv_Amp1, tv_Grab1, tv_Speaker2, tv_Amp2, tv_Grab2, tv_Speaker3, tv_Amp3, tv_Grab3; //The text views of the +-, tv = textview
    CheckBox cb_leaveStart1, cb_leaveStart2, cb_leaveStart3;
    int[] speakerNum = {0,0,0};
    int[] ampNum = {0,0,0};
    int [] grabNum = {0,0,0};
    boolean[] boolLeaveStart = {false, false, false};
    TextView teamNum_1_txt, teamNum_2_txt, teamNum_3_txt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_input_auton_three, container, false);

        teamNum_1_txt = view.findViewById(R.id.auton_teamNum1);
        teamNum_2_txt = view.findViewById(R.id.auton_teamNum2);
        teamNum_3_txt = view.findViewById(R.id.auton_teamNum3);

        //region Team 1
        //Set the pm to the right view. We can then call the children of the view
        pm_Speaker1 = (View) view.findViewById(R.id.pm_Speaker_1);
        Button plusBtn = pm_Speaker1.findViewById(R.id.plus_button);
        Button minusBtn = pm_Speaker1.findViewById(R.id.minus_button);
        tv_Speaker1 = pm_Speaker1.findViewById(R.id.pre_match_text); //this is the inner text. This is unique as it needs to be called later
        tv_Speaker1.setText(String.valueOf(speakerNum[0]));
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum[0]++;
                tv_Speaker1.setText(String.valueOf(speakerNum[0]));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum[0]--;
                tv_Speaker1.setText(String.valueOf(speakerNum[0]));
            }
        });

        //Doing the same thing again
        pm_Amp1 = (View) view.findViewById(R.id.pm_Amp_1);
        Button plusBtn2 = pm_Amp1.findViewById(R.id.plus_button);
        Button minusBtn2 = pm_Amp1.findViewById(R.id.minus_button);
        tv_Amp1 = pm_Amp1.findViewById(R.id.pre_match_text);
        tv_Amp1.setText(String.valueOf(ampNum[0]));

        plusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum[0]++;
                tv_Amp1.setText(String.valueOf(ampNum[0]));
            }
        });
        minusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum[0]--;
                tv_Amp1.setText(String.valueOf(ampNum[0]));
            }
        });

        pm_Grab1 = (View) view.findViewById(R.id.pm_BS_1);
        Button plusBtn3 = pm_Grab1.findViewById(R.id.plus_button);
        Button minusBtn3 = pm_Grab1.findViewById(R.id.minus_button);
        tv_Grab1 = pm_Grab1.findViewById(R.id.pre_match_text);
        tv_Grab1.setText(String.valueOf(grabNum[0]));

        plusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum[0] ++;
                tv_Grab1.setText(String.valueOf(grabNum[0]));
            }
        });
        minusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum[0] --;
                tv_Grab1.setText(String.valueOf(grabNum[0]));
            }
        });

        cb_leaveStart1 = view.findViewById(R.id.cb_LeaveStart_1);
        cb_leaveStart1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                boolLeaveStart[0] = cb_leaveStart1.isChecked();
            }
        });
        //endregion
        
        //region Team 2
        //Set the pm to the right view. We can then call the children of the view
        pm_Speaker2 = (View) view.findViewById(R.id.pm_Speaker_2);
        Button plusBtn4 = pm_Speaker2.findViewById(R.id.plus_button);
        Button minusBtn4 = pm_Speaker2.findViewById(R.id.minus_button);
        tv_Speaker2 = pm_Speaker2.findViewById(R.id.pre_match_text); //this is the inner text. This is unique as it needs to be called later
        tv_Speaker2.setText(String.valueOf(speakerNum[1]));
        plusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum[1]++;
                tv_Speaker2.setText(String.valueOf(speakerNum[1]));
            }
        });
        minusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum[1]--;
                tv_Speaker2.setText(String.valueOf(speakerNum[1]));
            }
        });

        //Doing the same thing again
        pm_Amp2 = (View) view.findViewById(R.id.pm_Amp_2);
        Button plusBtn5 = pm_Amp2.findViewById(R.id.plus_button);
        Button minusBtn5 = pm_Amp2.findViewById(R.id.minus_button);
        tv_Amp2 = pm_Amp2.findViewById(R.id.pre_match_text);
        tv_Amp2.setText(String.valueOf(ampNum[1]));

        plusBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum[1]++;
                tv_Amp2.setText(String.valueOf(ampNum[1]));
            }
        });
        minusBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum[1]--;
                tv_Amp2.setText(String.valueOf(ampNum[1]));
            }
        });

        pm_Grab2 = (View) view.findViewById(R.id.pm_BS_2);
        Button plusBtn6 = pm_Grab2.findViewById(R.id.plus_button);
        Button minusBtn6 = pm_Grab2.findViewById(R.id.minus_button);
        tv_Grab2 = pm_Grab2.findViewById(R.id.pre_match_text);
        tv_Grab2.setText(String.valueOf(grabNum[1]));

        plusBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum[1] ++;
                tv_Grab2.setText(String.valueOf(grabNum[1]));
            }
        });
        minusBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum[1] --;
                tv_Grab2.setText(String.valueOf(grabNum[1]));
            }
        });

        cb_leaveStart2 = view.findViewById(R.id.cb_LeaveStart_2);
        cb_leaveStart2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                boolLeaveStart[1] = cb_leaveStart2.isChecked();
            }
        });
        //endregion

        //region Team 3
        //Set the pm to the right view. We can then call the children of the view
        pm_Speaker3 = (View) view.findViewById(R.id.pm_Speaker_3);
        Button plusBtn7 = pm_Speaker3.findViewById(R.id.plus_button);
        Button minusBtn7 = pm_Speaker3.findViewById(R.id.minus_button);
        tv_Speaker3 = pm_Speaker3.findViewById(R.id.pre_match_text); //this is the inner text. This is unique as it needs to be called later
        tv_Speaker3.setText(String.valueOf(speakerNum[2]));
        plusBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum[2]++;
                tv_Speaker3.setText(String.valueOf(speakerNum[2]));
            }
        });
        minusBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum[2]--;
                tv_Speaker3.setText(String.valueOf(speakerNum[2]));
            }
        });

        //Doing the same thing again
        pm_Amp3 = (View) view.findViewById(R.id.pm_Amp_3);
        Button plusBtn8 = pm_Amp3.findViewById(R.id.plus_button);
        Button minusBtn8 = pm_Amp3.findViewById(R.id.minus_button);
        tv_Amp3 = pm_Amp3.findViewById(R.id.pre_match_text);
        tv_Amp3.setText(String.valueOf(ampNum[2]));

        plusBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum[2]++;
                tv_Amp3.setText(String.valueOf(ampNum[2]));
            }
        });
        minusBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum[2]--;
                tv_Amp3.setText(String.valueOf(ampNum[2]));
            }
        });

        pm_Grab3 = (View) view.findViewById(R.id.pm_BS_3);
        Button plusBtn9 = pm_Grab3.findViewById(R.id.plus_button);
        Button minusBtn9 = pm_Grab3.findViewById(R.id.minus_button);
        tv_Grab3 = pm_Grab3.findViewById(R.id.pre_match_text);
        tv_Grab3.setText(String.valueOf(grabNum[2]));

        plusBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum[2] ++;
                tv_Grab3.setText(String.valueOf(grabNum[2]));
            }
        });
        minusBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum[2] --;
                tv_Grab3.setText(String.valueOf(grabNum[2]));
            }
        });

        cb_leaveStart3 = view.findViewById(R.id.cb_LeaveStart_3);
        cb_leaveStart3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                boolLeaveStart[2] = cb_leaveStart3.isChecked();
            }
        });
        //endregion
        
        return view;
    }

   
    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.plus_button:
                cubesNum ++;
                textView.setText(String.valueOf(cubesNum));
                Log.d("HELLO", String.valueOf(cubesNum));
                break;
            case R.id.minus_button:
                cubesNum --;
                textView.setText(String.valueOf(cubesNum));
                Log.d("HELLO", String.valueOf(cubesNum));
                break;
            case :
                cubesNum --;
                textView.setText(String.valueOf(cubesNum));
                Log.d("HELLO", String.valueOf(cubesNum));
                break;
        }*/
    }

    public void sendData(){
        passData(boolLeaveStart, speakerNum, ampNum, grabNum);
    }

    public void retriveData(ContentValues data){
        speakerNum[0] = data.getAsInteger("data");
    }

    public interface AutonOnDataPass {
        public void AutonOnDataPass(boolean[] autoLeaveSpot, int[] autoSpeaker, int[] autoAmp, int[] autoGrab);
    }

    AutonOnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (AutonOnDataPass) context;
    }

    public void passData(boolean[] autoLeaveSpot, int[] autoSpeaker, int[] autoAmp, int[] autoGrab) {
        dataPasser.AutonOnDataPass(autoLeaveSpot, autoSpeaker, autoAmp, autoGrab);
    }

    void updateTeamNums(){
        Input_Three activity = (Input_Three) getActivity();
        int[] teamNums = activity.getTeamNums();
        teamNum_1_txt.setText(String.valueOf(teamNums[0]));
        teamNum_2_txt.setText(String.valueOf(teamNums[1]));
        teamNum_3_txt.setText(String.valueOf(teamNums[2]));
    }
}