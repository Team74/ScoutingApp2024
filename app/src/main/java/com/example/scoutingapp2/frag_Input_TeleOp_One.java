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
 * Use the {@link frag_Input_TeleOp_One#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Input_TeleOp_One extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_Input_TeleOp_One() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_Input_TeleOp_One.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_Input_TeleOp_One newInstance(String param1, String param2) {
        frag_Input_TeleOp_One fragment = new frag_Input_TeleOp_One();
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
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_input_teleop_one, container, false);
        View plusOrMinus = (View) view.findViewById(R.id.plusOrMinus);
        Button plusBtn = plusOrMinus.findViewById(R.id.plus_button);
        Button minusBtn = plusOrMinus.findViewById(R.id.minus_button);
        plusBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        textView = plusOrMinus.findViewById(R.id.pre_match_text);
        textView.setText(String.valueOf(cubesNum));


        return view;
    }

    int cubesNum = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        }
    }

    public void sendData(){
        passData(cubesNum);
    }

    public void retriveData(ContentValues data){
        cubesNum = data.getAsInteger("data");
    }

    public interface TeleOpOnDataPass {
        public void TeleOpOnDataPass(int data);
    }

    TeleOpOnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (TeleOpOnDataPass) context;
    }

    public void passData(int data) {
        dataPasser.TeleOpOnDataPass(data);
    }
}