package com.example.ashrafulhassan.calculatorremind;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashrafulhassan.calculatorremind.adapter.HistoryAdapter;
import com.example.ashrafulhassan.calculatorremind.db.DataBaseHelper;
import com.example.ashrafulhassan.calculatorremind.model.History;
import com.example.ashrafulhassan.calculatorremind.utils.SpacesItemDecoration;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private TextView e1;
    private EditText e2;
    private int count=0;
    private String expression="";
    private String text="";
    private Double result=0.0;

    private ImageView addRemarks;

    private DataBaseHelper dataBaseHelper;

    private View hiddenPanel;

    private SlidingUpPanelLayout mLayout;

    private String timeStamp = "";

    ArrayList<History> histories;
    RecyclerView historyRecyclerview;
    HistoryAdapter historyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editText1);
        e2 = findViewById(R.id.editText2);

        hiddenPanel = findViewById(R.id.hidden_panel);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        addRemarks = findViewById(R.id.addRemarks);

        e2.setText("0");

        histories = dataBaseHelper.getHistory();


        historyRecyclerview = findViewById(R.id.historyRecyclerview);
        historyRecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        historyRecyclerview.setLayoutManager(mLayoutManager);
        historyAdapter = new HistoryAdapter(histories, getApplicationContext());
        historyRecyclerview.setAdapter(historyAdapter);

        historyRecyclerview.addItemDecoration(new SpacesItemDecoration(10));

        addRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dialog for first ADD item
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.custom_dialog_edit, null);
                builder.setView(dialogView);

                final EditText editRemarks = dialogView.findViewById(R.id.editRemarks);

                builder.setTitle("My remarks");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String remarks = editRemarks.getText().toString();

                        if (!expression.equals("0.0")){
                            dataBaseHelper.updateRemarks(remarks,timeStamp);
                            histories.clear();
                            histories = dataBaseHelper.getHistory();
                            historyAdapter.updateReceiptsList(histories);
                        }



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.num0:
                e2.setText(e2.getText()+"0");
                break;

            case R.id.num1:
                e2.setText(e2.getText()+"1");
                break;

            case R.id.num2:
                e2.setText(e2.getText()+"2");
                break;

            case R.id.num3:
                e2.setText(e2.getText()+"3");
                break;


            case R.id.num4:
                e2.setText(e2.getText()+"4");
                break;

            case R.id.num5:
                e2.setText(e2.getText()+"5");
                break;

            case R.id.num6:
                e2.setText(e2.getText()+"6");
                break;

            case R.id.num7:
                e2.setText(e2.getText()+"7");
                break;

            case R.id.num8:
                e2.setText(e2.getText()+"8");
                break;

            case R.id.num9:
                e2.setText(e2.getText()+"9");
                break;

            case R.id.dot:
                if(count==0 && e2.length()!=0)
                {
                    e2.setText(e2.getText()+".");
                    count++;
                }
                break;

            case R.id.clear:
                e1.setText("");
                e2.setText("");
                count=0;
                expression="";
                break;

            case R.id.backSpace:
                text=e2.getText().toString();
                if(text.length()>0)
                {
                    if(text.endsWith("."))
                    {
                        count=0;
                    }
                    String newText=text.substring(0,text.length()-1);
                    //to delete the data contained in the brackets at once
                    if(text.endsWith(")"))
                    {
                        char []a=text.toCharArray();
                        int pos=a.length-2;
                        int counter=1;
                        //to find the opening bracket position
                        for(int i=a.length-2;i>=0;i--)
                        {
                            if(a[i]==')')
                            {
                                counter++;
                            }
                            else if(a[i]=='(')
                            {
                                counter--;
                            }
                            //if decimal is deleted b/w brackets then count should be zero
                            else if(a[i]=='.')
                            {
                                count=0;
                            }
                            //if opening bracket pair for the last bracket is found
                            if(counter==0)
                            {
                                pos=i;
                                break;
                            }
                        }
                        newText=text.substring(0,pos);
                    }
                    //if e2 edit text contains only - sign or sqrt at last then clear the edit text e2
                    if(newText.equals("-")||newText.endsWith("sqrt"))
                    {
                        newText="";
                    }
                    //if pow sign is left at the last
                    else if(newText.endsWith("^"))
                        newText=newText.substring(0,newText.length()-1);

                    e2.setText(newText);
                }
                break;

            case R.id.plus:
                operationClicked("+");
                break;

            case R.id.minus:
                operationClicked("-");
                break;

            case R.id.divide:
                operationClicked("/");
                break;

            case R.id.multiply:
                operationClicked("*");
                break;

            case R.id.sqrt:
                if(e2.length()!=0)
                {
                    text=e2.getText().toString();
                    e2.setText("sqrt(" + text + ")");
                }
                break;

            case R.id.square:
                if(e2.length()!=0)
                {
                    text=e2.getText().toString();
                    e2.setText("("+text+")^2");
                }
                break;

            case R.id.posneg:
                if(e2.length()!=0)
                {
                    String s=e2.getText().toString();
                    char arr[]=s.toCharArray();
                    if(arr[0]=='-')
                        e2.setText(s.substring(1,s.length()));
                    else
                        e2.setText("-"+s);
                }
                break;

            case R.id.equal:

                if(e1.length()!=0||e2.length()!=0)
                {
                    text=e2.getText().toString();
                    expression=e1.getText().toString()+text;
                    Log.v("expression:::  ",expression);
                }
                e1.setText("");
                if(expression.length()==0){
                    expression="0.0"; Log.v("expression:::  ",expression);
                }
                Log.v("expression### ",expression);

               // DoubleEvaluator evaluator = new DoubleEvaluator();
                try
                {
                    //evaluate the expression
                    // result=new ExtendedDoubleEvaluator().evaluate(expression);
                    //insert expression and result in sqlite database if expression is valid and not 0.0
                    Double result = new DoubleEvaluator().evaluate(expression);

                    //Double resul = new DoubleEvaluator().evaluate(expression);

                    if(!expression.equals("0.0")){
                        timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        dataBaseHelper.addHistory(expression, ""+result, "", timeStamp);
                        histories.clear();
                        histories = dataBaseHelper.getHistory();
                        historyAdapter.updateReceiptsList(histories);

                    }


                    Log.v("data%%%%  ",""+dataBaseHelper.getHistory().get(0).getExpression());
                    e2.setText(result+"");
                }
                catch (Exception e)
                {
                    e2.setText("Invalid Expression");
                    e1.setText("");
                    expression="";
                    e.printStackTrace();
                }
                break;

            case R.id.openBracket:
                e1.setText(e1.getText()+"(");
                break;

            case R.id.closeBracket:
                /*String s = e1.getText().toString()+e2.getText().toString()+")";
                e1.setText(s);
                Log.v("e2:   ",s);*/
                if(e2.length()!=0){
                    String s = e1.getText().toString()+e2.getText().toString()+")";
                    e1.setText(s); e2.setText("");
                    Log.v("e2:   ",s);
                }

                else
                    e1.setText(e1.getText() + ")");
                break;


            /*case R.id.history:
                Intent i=new Intent(this,History.class);
                i.putExtra("calcName","STANDARD");
                startActivity(i);
                break;*/
        }
    }

    private void operationClicked(String op)
    {
        if(e1.length()!=0 || e2.length()!=0)
        {
            String text=e2.getText().toString();
            e1.setText(e1.getText() + text+op);
            e2.setText("");
            count=0;
        }
        else
        {
            String text=e1.getText().toString();
            if(text.length()>0)
            {
                String newText=text.substring(0,text.length()-1)+op;
                e1.setText(newText);
            }
        }
    }

    public void slideUpDown(final View view) {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
        }
    }

    private boolean isPanelShown() {
        return hiddenPanel.getVisibility() == View.VISIBLE;
    }

    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        finish();
        System.exit(0);
    }
}
