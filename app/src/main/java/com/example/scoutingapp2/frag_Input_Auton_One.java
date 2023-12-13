package com.example.scoutingapp2;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_Input_Auton_One#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Input_Auton_One extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_Input_Auton_One() {
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
    public static frag_Input_Auton_One newInstance(String param1, String param2) {
        frag_Input_Auton_One fragment = new frag_Input_Auton_One();
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
    View plusOrMinus1, plusOrMinus2, plusOrMinus3, plusOrMinus4, plusOrMinus5, plusOrMinus6;
    TextView textView, textView2, textView3, textView4, textView5, textView6;
    int cubesNumHigh, cubesNumMid, cubesNumLow, conesNumHigh, conesNumMid, conesNumLow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_input_auton_one, container, false);
        plusOrMinus1 = (View) view.findViewById(R.id.plusOrMinus);
        Button plusBtn = plusOrMinus1.findViewById(R.id.plus_button);
        Button minusBtn = plusOrMinus1.findViewById(R.id.minus_button);
        textView = plusOrMinus1.findViewById(R.id.pre_match_text);
        textView.setText(String.valueOf(cubesNumHigh));
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubesNumHigh++;
                textView.setText(String.valueOf(cubesNumHigh));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubesNumHigh--;
                textView.setText(String.valueOf(cubesNumHigh));
            }
        });

        plusOrMinus2 = (View) view.findViewById(R.id.plusOrMinus2);
        Button plusBtn2 = plusOrMinus2.findViewById(R.id.plus_button);
        Button minusBtn2 = plusOrMinus2.findViewById(R.id.minus_button);
        textView2 = plusOrMinus2.findViewById(R.id.pre_match_text);
        textView2.setText(String.valueOf(cubesNumMid));

        plusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubesNumMid++;
                textView2.setText(String.valueOf(cubesNumMid));
            }
        });
        minusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubesNumMid--;
                textView2.setText(String.valueOf(cubesNumMid));
            }
        });

        plusOrMinus3 = (View) view.findViewById(R.id.plusOrMinus3);
        Button plusBtn3 = plusOrMinus3.findViewById(R.id.plus_button);
        Button minusBtn3 = plusOrMinus3.findViewById(R.id.minus_button);
        textView3 = plusOrMinus3.findViewById(R.id.pre_match_text);
        textView3.setText(String.valueOf(cubesNumLow));

        plusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubesNumLow ++;
                textView3.setText(String.valueOf(cubesNumLow));
            }
        });
        minusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubesNumLow --;
                textView3.setText(String.valueOf(cubesNumLow));
            }
        });

        plusOrMinus4 = (View) view.findViewById(R.id.plusOrMinus4);
        Button plusBtn4 = plusOrMinus4.findViewById(R.id.plus_button);
        Button minusBtn4 = plusOrMinus4.findViewById(R.id.minus_button);
        textView4 = plusOrMinus4.findViewById(R.id.pre_match_text);
        textView4.setText(String.valueOf(conesNumHigh));

        plusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conesNumHigh++;
                textView4.setText(String.valueOf(conesNumHigh));
            }
        });
        minusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conesNumHigh--;
                textView4.setText(String.valueOf(conesNumHigh));
            }
        });

        plusOrMinus5 = (View) view.findViewById(R.id.plusOrMinus5);
        Button plusBtn5 = plusOrMinus5.findViewById(R.id.plus_button);
        Button minusBtn5 = plusOrMinus5.findViewById(R.id.minus_button);
        textView5 = plusOrMinus5.findViewById(R.id.pre_match_text);
        textView5.setText(String.valueOf(conesNumMid));

        plusBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conesNumMid++;
                textView5.setText(String.valueOf(conesNumMid));
            }
        });
        minusBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conesNumMid--;
                textView5.setText(String.valueOf(conesNumMid));
            }
        });

        plusOrMinus6 = (View) view.findViewById(R.id.plusOrMinus6);
        Button plusBtn6 = plusOrMinus6.findViewById(R.id.plus_button);
        Button minusBtn6 = plusOrMinus6.findViewById(R.id.minus_button);
        textView6 = plusOrMinus6.findViewById(R.id.pre_match_text);
        textView6.setText(String.valueOf(conesNumLow));

        plusBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conesNumLow++;
                textView6.setText(String.valueOf(conesNumLow));
            }
        });
        minusBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conesNumLow--;
                textView6.setText(String.valueOf(conesNumLow));
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
        passData(cubesNumLow, cubesNumMid, cubesNumHigh, conesNumLow, conesNumMid, conesNumHigh);
    }

    public void retriveData(ContentValues data){
        cubesNumHigh = data.getAsInteger("data");
    }

    public interface AutonOnDataPass {
        public void AutonOnDataPass(int cubeLow, int cubeMid, int cubeHigh, int coneLow, int coneMid, int coneHigh);
    }

    AutonOnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (AutonOnDataPass) context;
    }

    public void passData(int cubeLow, int cubeMid, int cubeHigh, int coneLow, int coneMid, int coneHigh) {
        dataPasser.AutonOnDataPass(cubeLow, cubeMid, cubeHigh, coneLow, coneMid, coneHigh);
    }
}