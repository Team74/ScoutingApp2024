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
    View pm_Speaker, pm_Amp, pm_Grab; //The plus or minus. pm = +-
    TextView tv_Speaker, tv_Amp, tv_Grab; //The text views of the +-, tv = textview
    CheckBox cb_leaveStart;
    int speakerNum, ampNum, grabNum;
    Boolean boolLeaveStart = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_input_auton_three, container, false);
        //Set the pm to the right view. We can then call the children of the view
        pm_Speaker = (View) view.findViewById(R.id.pm_Speaker_1);
        Button plusBtn = pm_Speaker.findViewById(R.id.plus_button);
        Button minusBtn = pm_Speaker.findViewById(R.id.minus_button);
        tv_Speaker = pm_Speaker.findViewById(R.id.pre_match_text); //this is the inner text. This is unique as it needs to be called later
        tv_Speaker.setText(String.valueOf(speakerNum));
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum++;
                tv_Speaker.setText(String.valueOf(speakerNum));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerNum--;
                tv_Speaker.setText(String.valueOf(speakerNum));
            }
        });

        //Doing the same thing again
        pm_Amp = (View) view.findViewById(R.id.pm_Amp_1);
        Button plusBtn2 = pm_Amp.findViewById(R.id.plus_button);
        Button minusBtn2 = pm_Amp.findViewById(R.id.minus_button);
        tv_Amp = pm_Amp.findViewById(R.id.pre_match_text);
        tv_Amp.setText(String.valueOf(ampNum));

        plusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum++;
                tv_Amp.setText(String.valueOf(ampNum));
            }
        });
        minusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampNum--;
                tv_Amp.setText(String.valueOf(ampNum));
            }
        });

        pm_Grab = (View) view.findViewById(R.id.pm_BS_1);
        Button plusBtn3 = pm_Grab.findViewById(R.id.plus_button);
        Button minusBtn3 = pm_Grab.findViewById(R.id.minus_button);
        tv_Grab = pm_Grab.findViewById(R.id.pre_match_text);
        tv_Grab.setText(String.valueOf(grabNum));

        plusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum ++;
                tv_Grab.setText(String.valueOf(grabNum));
            }
        });
        minusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabNum --;
                tv_Grab.setText(String.valueOf(grabNum));
            }
        });

        cb_leaveStart = view.findViewById(R.id.cb_LeaveStart_1);
        cb_leaveStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                boolLeaveStart = cb_leaveStart.isChecked();
            }
        });
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
        speakerNum = data.getAsInteger("data");
    }

    public interface AutonOnDataPass {
        public void AutonOnDataPass(boolean autoLeaveSpot, int autoSpeaker, int autoAmp, int autoGrab);
    }

    AutonOnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (AutonOnDataPass) context;
    }

    public void passData(boolean autoLeaveSpot, int autoSpeaker, int autoAmp, int autoGrab) {
        dataPasser.AutonOnDataPass(autoLeaveSpot, autoSpeaker, autoAmp, autoGrab);
    }
}