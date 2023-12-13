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
import android.widget.EditText;
import android.widget.TextView;


//TODO delete the starting code if it become unused
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_Input_PreMatch_One#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Input_PreMatch_One extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_Input_PreMatch_One() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_Input_Team_One.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_Input_PreMatch_One newInstance(String param1, String param2) {
        frag_Input_PreMatch_One fragment = new frag_Input_PreMatch_One();
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
    EditText teamNum_et;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_input_prematch_one, container, false);

        teamNum_et = view.findViewById(R.id.teamNum_et);
        teamNum_et.setText(String.valueOf(teamNum));

        // Inflate the layout for this fragment
        return view;
    }

    int teamNum;
    @Override
    public void onClick(View v) {

    }

    public void sendData(){
        passData(Integer.parseInt(teamNum_et.getText().toString()));
    }

    public void retriveData(ContentValues data){ //cant set the text in here as this is before the layout is inflated
        teamNum = data.getAsInteger("data");
    }

    public interface PreMatchOnDataPass {
        public void PreMatchOnDataPass(int data);
    }

    PreMatchOnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (PreMatchOnDataPass) context;
    }

    public void passData(int data) {
        dataPasser.PreMatchOnDataPass(data);
    }
}