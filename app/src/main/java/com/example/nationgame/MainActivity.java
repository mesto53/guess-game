package com.example.nationgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AssetManager asset;
    private static int nbquetions=10;
    private String flag_name[],correct;
    private ImageView img;
    private TextView tq,choosenchioses,choosenregion;
    private static int  corect=0,quetions=0;
    private ArrayList<Button> ButtonList = new ArrayList<Button>();
    private static int nb = 3;
    LinearLayout l1;
    ArrayList<Integer> array = new ArrayList<Integer>();
    private static boolean checked = false;
    private static String region = "all";
    public int v,v1,nb_names,i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
        tq = findViewById(R.id.nbquetion);
        choosenregion=findViewById(R.id.choosenregion);
        choosenchioses =findViewById(R.id.choosenchioses);
        asset = this.getAssets();
        try {
            flag_name= asset.list("png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        l1= findViewById(R.id.l1);
        if(ButtonList.isEmpty()){
            for (int i = 0;i<3;i++){
                Button btn = new Button(this);
                btn.setId(i);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(30, 20, 30, 20);
                btn.setLayoutParams(layoutParams);
                btn.setOnClickListener(this::check);
                ButtonList.add(btn);
                l1.addView(btn);
            }
            fill();
        }else{
            for(i = 0;i<ButtonList.size();i++){
                ButtonList.get(i).setText(flag_name[array.get(i)].split("-")[0]);
            }
        }
    }


///making the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.africa:
                region = "africa";
                quetions=0;
                corect=0;
                fill(nb);
                return true;
            case R.id.asia:
                region = "asia";
                quetions=0;
                corect=0;
                fill(nb);
                return true;
            case R.id.amirca:
                region = "amirca";
                quetions=0;
                corect=0;
                fill(nb);
                return true;
            case R.id.all:
                region = "all";
                quetions=0;
                corect=0;
                fill(nb);
                return true;
            case R.id.europe:
                region = "europe";
                quetions=0;
                corect=0;
                fill(nb);
                return true;
            case R.id.choise3:
                quetions=0;
                corect=0;
                fill(3);
                return true;
            case R.id.choise6:
                quetions=0;
                corect=0;
                fill(6);
                return true;
            case R.id.choise9:
                quetions=0;
                corect=0;
                fill(9);
                return true;
            case R.id.refresh:
                refresh();
                return true;
            case R.id.nb1:
                quetions=0;
                corect=0;
                nbquetions = 10;
                fill(nb);
                return true;
            case R.id.nb2:
                quetions=0;
                corect=0;
                nbquetions = 20;
                fill(nb);
                return true;
            case R.id.nb3:
                quetions=0;
                corect=0;
                nbquetions = 30;
                fill(nb);
                return true;
            case R.id.nb4:
                quetions=0;
                corect=0;
                nbquetions = 40;
                fill(nb);
                return true;
            default:
                return super.onOptionsItemSelected(item);}}


//refresh if the user want to restart the game
     public void refresh() {
         AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
         alertDialog.setTitle("restart");
         alertDialog.setMessage("you will start the game again");
         alertDialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 quetions=0;
                 corect=0;
                 if(nb==3) fill(3);
                 else if(nb==6) fill(6);
                 else if(nb==9) fill(9);
             }
         });
         alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         });
         alertDialog.show();
     }


    //fill and discuss what moode he want
    public void fill(int choise )
    {
        if(choise>nb){
            for (int i =nb ;i<choise;i++){
                Button btn = new Button(this);
                btn.setId(i);
                btn.setLayoutParams(new ViewGroup.LayoutParams(-2,-2));
                btn.setOnClickListener(this::check);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(30, 20, 30, 20);
                btn.setLayoutParams(layoutParams);
                ButtonList.add(btn);
                l1.addView(btn);
            }
            nb = choise;
            fill();
        }else if(choise<nb){
             for(int i = nb-1;i>=choise;i--){
                 Button bot = l1.findViewById(i);
                 ButtonList.remove(i);
                 l1.removeView(bot);
             }
             nb=choise;
             fill();
        }else{
            fill();
        }
    }

    //check the correct answer
    public void check(View v)
    {
        String s = ((Button) v).getText().toString();
        if(s.equals(correct)){
            ((Button) v).setBackgroundColor(Color.GREEN);
            corect++;
        }
        else{
            ((Button) v).setBackgroundColor(Color.RED);
        }
        for(int i=0;i<ButtonList.size();i++){
            (ButtonList.get(i)).setEnabled(false);
        }
        checked =true;
    }

    //button next to the net stage
    public void next(View v){
        if(checked){
            if(quetions==nbquetions ){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("restart");
                alertDialog.setMessage("you get "+corect+"/"+nbquetions);
                alertDialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        quetions=0;
                        corect=0;
                        if(nb==3) fill(3);
                        else if(nb==6) fill(6);
                        else if(nb==9) fill(9);
                    }
                }).show();}
            else{
                if(nb==3) fill(3);
                else if(nb==6) fill(6);
                else if(nb==9) fill(9);}
        }
        checked=false;
    }

    //fill the image and buttons
    public void fill(){
        choosenchioses.setText("Number of choises : "+nb);
        choosenregion.setText("Region : "+region);
        quetions+=1;
        String v1_name=null,v2_name=null;
         v1=0;
         nb_names = flag_name.length;
         v = (int) (Math.random()*nb);

        for( i = 0;i<ButtonList.size();i++){
            v1 =(int) (Math.random()*nb_names);
            String vname = flag_name[v1].split("-")[0];
            if(region == "amirca"&& (!vname.equals("South_America") && !vname.equals("North_America"))){
                i--;
                continue;
            }else if(region=="asia"&& !vname.equals("Asia")){
                i--;
                continue;
            } else if (region == "europe"&& !vname.equals("Europe")) {
                i--;
                continue;
            }else if(region=="africa" && !vname.equals("Africa")){
                i--;
                continue;
            }
            array.add(v1);
            v1_name = flag_name[v1].split("-")[1];
            ButtonList.get(i).setText(v1_name.split(".png")[0]);
        }
        int v2 = (int) (Math.random()*i);
        v2_name = flag_name[array.get(v2)].split("-")[1];
        Drawable dr = null;
        try {
            dr = Drawable.createFromStream(asset.open("png/"+flag_name[array.get(v2)]), v2_name);
        }catch (Exception ex){
            System.out.println(ex);}
        img.setImageDrawable(dr);
        correct = v2_name.split(".png")[0];
        for(i=0;i<ButtonList.size();i++) ButtonList.get(i).setEnabled(true);
        for(i=0;i<ButtonList.size();i++) ButtonList.get(i).setBackgroundColor(Color.rgb(138,43,226));
        tq.setText("Quetion "+quetions+" of "+nbquetions);
    }

    @Override
    public void onSaveInstanceState(Bundle b){
        super.onSaveInstanceState(b);
        b.putInt("nb",nb);
        b.putString("region",region);
        b.putInt("nbquetions",nbquetions);
        b.putInt("quetions",quetions);
        b.putInt("correct",corect);
        b.putIntegerArrayList("array",array);
        b.putInt("v",v);
        //b.putParcelableArrayList("ButtonList", (ArrayList<? extends Parcelable>) ButtonList);
        }


    public void onRestoreInstanceState(Bundle b){
        super.onRestoreInstanceState(b);
        if(b!=null){
            nb=b.getInt("nb");
            region=b.getString("region");
            //ButtonList = b.getParcelableArrayList("ButtonList");
            nbquetions=b.getInt("nbquetions");
            quetions = b.getInt("quetions");
            corect= b.getInt("correct");
            array = b.getIntegerArrayList("array");
            v= b.getInt("v");

        }
    }

}