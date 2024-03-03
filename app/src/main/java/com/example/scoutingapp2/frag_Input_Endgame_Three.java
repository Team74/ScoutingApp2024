package com.example.scoutingapp2;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_Input_Endgame_Three#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Input_Endgame_Three extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_Input_Endgame_Three() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_input_endgame_three.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_Input_Endgame_Three newInstance(String param1, String param2) {
        frag_Input_Endgame_Three fragment = new frag_Input_Endgame_Three();
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
    View pm_Trapdoor1, pm_Trapdoor2,pm_Trapdoor3; //The plus or minus. pm = +-
    TextView tv_Trapdoor1, tv_Trapdoor2, tv_Trapdoor3; //The text views of the +-, tv = textview
    int[] TrapdoorNum = {0,0,0};
    int[] climbAmount = {0,0,0};
    Spinner climb_1, climb_2, climb_3;
    TextView teamNum_1_txt, teamNum_2_txt, teamNum_3_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_input_endgame_three, container, false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.balance, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        teamNum_1_txt = view.findViewById(R.id.endgame_teamNum1);
        teamNum_2_txt = view.findViewById(R.id.endgame_teamNum2);
        teamNum_3_txt = view.findViewById(R.id.endgame_teamNum3);

        //region Team 1
        //Set the pm to the right view. We can then call the children of the view
        pm_Trapdoor1 = (View) view.findViewById(R.id.pm_Trapdoor_1);
        Button plusBtn = pm_Trapdoor1.findViewById(R.id.plus_button);
        Button minusBtn = pm_Trapdoor1.findViewById(R.id.minus_button);
        tv_Trapdoor1 = pm_Trapdoor1.findViewById(R.id.pre_match_text); //this is the inner text. This is unique as it needs to be called later
        tv_Trapdoor1.setText(String.valueOf(TrapdoorNum[0]));
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrapdoorNum[0]++;
                tv_Trapdoor1.setText(String.valueOf(TrapdoorNum[0]));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrapdoorNum[0]--;
                tv_Trapdoor1.setText(String.valueOf(TrapdoorNum[0]));
            }
        });

        climb_1 = view.findViewById(R.id.climb_1_spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climb_1.setAdapter(adapter);
        //endregion

        //region Team 2
        //Set the pm to the right view. We can then call the children of the view
        pm_Trapdoor2 = (View) view.findViewById(R.id.pm_Trapdoor_2);
        Button plusBtn4 = pm_Trapdoor2.findViewById(R.id.plus_button);
        Button minusBtn4 = pm_Trapdoor2.findViewById(R.id.minus_button);
        tv_Trapdoor2 = pm_Trapdoor2.findViewById(R.id.pre_match_text); //this is the inner text. This is unique as it needs to be called later
        tv_Trapdoor2.setText(String.valueOf(TrapdoorNum[1]));
        plusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrapdoorNum[1]++;
                tv_Trapdoor2.setText(String.valueOf(TrapdoorNum[1]));
            }
        });
        minusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrapdoorNum[1]--;
                tv_Trapdoor2.setText(String.valueOf(TrapdoorNum[1]));
            }
        });

        climb_2 = view.findViewById(R.id.climb_2_spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climb_2.setAdapter(adapter);
        //endregion

        //region Team 3
        //Set the pm to the right view. We can then call the children of the view
        pm_Trapdoor3 = (View) view.findViewById(R.id.pm_Trapdoor_3);
        Button plusBtn7 = pm_Trapdoor3.findViewById(R.id.plus_button);
        Button minusBtn7 = pm_Trapdoor3.findViewById(R.id.minus_button);
        tv_Trapdoor3 = pm_Trapdoor3.findViewById(R.id.pre_match_text); //this is the inner text. This is unique as it needs to be called later
        tv_Trapdoor3.setText(String.valueOf(TrapdoorNum[2]));
        plusBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrapdoorNum[2]++;
                tv_Trapdoor3.setText(String.valueOf(TrapdoorNum[2]));
            }
        });
        minusBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrapdoorNum[2]--;
                tv_Trapdoor3.setText(String.valueOf(TrapdoorNum[2]));
            }
        });

        climb_3 = view.findViewById(R.id.climb_3_spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climb_3.setAdapter(adapter);


        climb_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String balanceType = adapterView.getItemAtPosition(i).toString();
                switch (balanceType){
                    case "Did Not Attempt":
                        climbAmount[0] = 0;
                        break;
                    case "On Stage":
                        climbAmount[0] = 1;
                        break;
                    case "On Chain":
                        climbAmount[0] = 2;
                        break;
                    case "Harmonized":
                        climbAmount[0] = 3;
                        break;
                    default:
                        climbAmount[0] = 0;
                }
                Log.d("Balance", String.valueOf(climbAmount[0]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        climb_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String balanceType = adapterView.getItemAtPosition(i).toString();
                switch (balanceType){
                    case "Did Not Attempt":
                        climbAmount[1] = 0;
                        break;
                    case "On Stage":
                        climbAmount[1] = 1;
                        break;
                    case "On Chain":
                        climbAmount[1] = 2;
                        break;
                    case "Harmonized":
                        climbAmount[1] = 3;
                        break;
                    default:
                        climbAmount[1] = 0;
                }
                Log.d("Balance", String.valueOf(climbAmount[1]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        climb_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String balanceType = adapterView.getItemAtPosition(i).toString();
                switch (balanceType){
                    case "Did Not Attempt":
                        climbAmount[2] = 0;
                        break;
                    case "On Stage":
                        climbAmount[2] = 1;
                        break;
                    case "On Chain":
                        climbAmount[2] = 2;
                        break;
                    case "Harmonized":
                        climbAmount[2] = 3;
                        break;
                    default:
                        climbAmount[2] = 0;
                }
                Log.d("Balance", String.valueOf(climbAmount[2]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //endregion
        return view;
    }

    public void sendData(){
        passData(TrapdoorNum, climbAmount);
    }

    public void retriveData(ContentValues data){
        TrapdoorNum[0] = data.getAsInteger("data");
    }

    EndGameOpOnDataPass dataPasser;
    public interface EndGameOpOnDataPass {
        public void EndgameOnDataPass(int[] trapDoorNum, int[] climbAmount);
    }

    public void passData(int[] trapdoorNum, int[] climbAmount) {
        dataPasser.EndgameOnDataPass(trapdoorNum, climbAmount);
    }

    void updateTeamNums(){
        Input_Three activity = (Input_Three) getActivity();
        int[] teamNums = activity.getTeamNums();
        teamNum_1_txt.setText(String.valueOf(teamNums[0]));
        teamNum_2_txt.setText(String.valueOf(teamNums[1]));
        teamNum_3_txt.setText(String.valueOf(teamNums[2]));
    }
}