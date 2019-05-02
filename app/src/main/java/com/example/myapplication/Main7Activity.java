package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

import static java.lang.Double.NaN;
import static java.lang.Double.doubleToLongBits;

public class Main7Activity extends Activity {


    //Intent message from activity 1
    String message1;

    InputStream inputStream;
    InputStream inputStream2;
    String[] data;
    String[] data2;

    Vector<String>data1_line = new Vector<>();
    Vector<String>data2_line = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent in=getIntent();
        message1 = in.getStringExtra("userName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        //Reading from file data.csv
        inputStream = getResources().openRawResource(R.raw.data1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                data1_line.add(csvLine);
                data=csvLine.split(",");
                try{


                }catch (Exception e){
                    Log.e("Problem",e.toString());
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }

        //Reading data from data.csv (for user reviews)
        inputStream2 = getResources().openRawResource(R.raw.data2);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2));
        try {
            String csvLine2;
            while ((csvLine2 = reader2.readLine()) != null)
            {
                data2_line.add(csvLine2);
                data2=csvLine2.split(",");
                try{

                }catch (Exception e){
                    Log.e("Problem",e.toString());
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }


        int index = 1;
        String [] temp1;
        String [] temp3;
        Vector<String>game_number = new Vector<>();
        Vector<String>title = new Vector<>();
        Vector<String>year = new Vector<>();
        Vector<String>publisher = new Vector<>();
        Vector<String>genre = new Vector<>();
        Vector<String>platform = new Vector<>();
        Vector<String>metascore = new Vector<>();
        Vector<String>avg_userscore = new Vector<>();
        Vector<String>players = new Vector<>();
        Vector<String>review = new Vector<>();

        Vector<String>game_number2 = new Vector<>();
        Vector<String>title_data2 = new Vector<>();
        Vector<String>platform2 = new Vector<>();
        Vector<String>userscore2 = new Vector<>();
        Vector<String>review2 = new Vector<>();
        Vector<String>review_genre = new Vector<>();
        Vector<Vector>tf_idf = new Vector<>();
        Vector<Double>cosine_similarities = new Vector<>();


        //Separating data
        while (index < data1_line.size())
        {
            temp1=data1_line.get(index).split(",");
            game_number.add(temp1[0]);
            title.add(temp1[1]);
            year.add(temp1[2]);
            publisher.add(temp1[3]);
            genre.add(temp1[4]);
            platform.add(temp1[5]);
            metascore.add(temp1[6]);
            avg_userscore.add(temp1[7]);
            review.add("");
            index++;
        }

        //Separating data
        index=1; // omitting first column

        while (index < data2_line.size()) {
            temp3=data2_line.get(index).split(",");
            game_number2.add(temp3[0]);
            title_data2.add(temp3[1]);
            platform2.add(temp3[2]);
            userscore2.add(temp3[3]);
            //review2.add(temp3[4]);

            //Filtering data: deleting stopwords
            //Source: https://stackoverflow.com/questions/27685839/removing-stopwords-from-a-string-in-java
            Pattern p = Pattern.compile("\\b(i|me|my|myself|we|our|ours|ourselves|you|your|yours|yourselves|he|him|she|her|himself|herself|his|" +
                    "it|its|it's|itself|they|them|their|theirs|themselves|what|which|who|whom|this|that" +
                    "|these|those|am|is|are|was|were|be|been|being|have|has|had|having|do|does|did|a|an|the|and" +
                    "|but|if|or|because|as|until|while|of|at|by|for|with|about|against|between|into|through|during|" +
                    "before|after|above|below|to|from|up|down|in|out|on|off|over|under|again|further|then|once|here|" +
                    "there|when|where|why|how|all|any|both|each|few|more|most|other|some|such|no|nor|not|only|own|same" +
                    "|so|than|too|very|can|will|just|don|should|now|;|)\\b\\s?");
            Matcher m = p.matcher(temp3[4].toLowerCase()); // converting everything into lowercase
            String s = m.replaceAll(" ");
            review2.add(s);
            index++;
        }


        //Merging two file and text reviews
        index = 0;
        int index2=0;
        String merge;
        while (index < title.size()) {
            index2 = 0;
            while (index2 <title_data2.size() ) {
                if ( title.get(index).equalsIgnoreCase( title_data2.get(index2)) ){
                    review.set(index, review2.get(index2));
                }
                index2++;
            }
            index++;
        }

        index=0;
        String temp10;
        while (index < title.size()){
            Pattern p = Pattern.compile("\\b(i|me|my|myself|we|our|ours|ourselves|you|your|yours|yourselves|he|him|she|her|himself|herself|his|" +
                    "it|its|it's|itself|they|them|their|theirs|themselves|what|which|who|whom|this|that" +
                    "|these|those|am|is|are|was|were|be|been|being|have|has|had|having|do|does|did|a|an|the|and" +
                    "|but|if|or|because|as|until|while|of|at|by|for|with|about|against|between|into|through|during|" +
                    "before|after|above|below|to|from|up|down|in|out|on|off|over|under|again|further|then|once|here|" +
                    "there|when|where|why|how|all|any|both|each|few|more|most|other|some|such|no|nor|not|only|own|same" +
                    "|so|than|too|very|can|will|just|don|should|now|;|)\\b\\s?");
            temp10 = genre.get(index);
            Matcher m = p.matcher(temp10.toLowerCase()); // converting everything into lowercase
            String s = m.replaceAll(" ");

            merge = s + review.get(index).toLowerCase()+" "+publisher.get(index).toLowerCase()+" "+platform.get(index).toLowerCase()+genre.get(index);
            review_genre.add(merge);
            index++;
        }





        //User Query
        Vector<String> user_query = new Vector<>();
        //Counting words
        String trim = message1.trim();
        int user_query_length =  trim.split("\\s+").length;

        //Tokenizing user query
        StringTokenizer st = new StringTokenizer(message1.toLowerCase());
        while (st.hasMoreTokens()) {
            String temp_token = st.nextToken();
            if(temp_token.equalsIgnoreCase( "games" )|| temp_token.equalsIgnoreCase( "game's"))
                temp_token= "game";
            user_query.add(temp_token);
        }


        //Calculating idf
        Vector<Double> idf_value = new Vector<>();
        index=0;
        while (index<user_query.size()){
            idf_value.add(idf(review_genre,user_query.get(index)));
            index++;
        }

        //Calculating tf_idf for user query
        index=0;
        Vector<Double> user_query_tf_idf = new Vector<>();

        while (index<user_query.size()){
            user_query_tf_idf.add((tf(message1, user_query.get(index)))*idf_value.get(index));//Calculating tf_idf
            index++;
        }

        double docVector2[] = new double[user_query_tf_idf.size()];
        for (int i = 0; i < user_query_tf_idf.size(); i++) {
            docVector2[i] = user_query_tf_idf.get(i);
        }

        Vector<Integer> top10 = new Vector<>();

        //Calculating tf_idf for each doc
        index=0;
        Vector<Double> tf_value = new Vector<>();
        Vector<Double> tf_idf_value = new Vector<>();
        Vector<Vector> tf_vector = new Vector<>();
        while (index<title.size()){
            tf_idf_value.clear();
            index2 = 0;
            while (index2<user_query.size()){
                //tf_value.add(tf(review_genre.get(index), user_query.get(index2)));
                tf_idf_value.add((tf(review_genre.get(index), user_query.get(index2))* idf_value.get(index2)));//Calculating tf_idf


                index2++;
            }
            index2=0;
            cosine_similarities.add(cosineSimilarity(tf_idf_value,user_query_tf_idf));
            if((1.000000000-cosine_similarities.get(index))== 0) // Finding best match for output
                top10.add(index);
            index++;
        }

        if (top10.size() < 10 ) {
            index=0;
            while (index<cosine_similarities.size()) {
                if (((1.0-cosine_similarities.get(index)) > 0.0) && ((1.0-cosine_similarities.get(index)) <=0.1)) { top10.add(index); }
                if (((1.0-cosine_similarities.get(index)) > 0.1) && ((1.0-cosine_similarities.get(index)) <=0.2)) { top10.add(index); }
                if (top10.size()>=10)
                    break;;
                index++;
            }

        }

        if (top10.size() < 10 ) {
            index=0;
            while (index<cosine_similarities.size()) {
                if (((1.0-cosine_similarities.get(index)) > 0.2) && ((1.0-cosine_similarities.get(index)) <=0.3)) { top10.add(index); }
                if (((1.0-cosine_similarities.get(index)) > 0.3) && ((1.0-cosine_similarities.get(index)) <=0.4)) { top10.add(index); }
                if (top10.size()>=10)
                    break;;
                index++;
            }

        }
        if (top10.size() < 10 ) {
            index=0;
            while (index<cosine_similarities.size()) {
                if (((1.0-cosine_similarities.get(index)) > 0.4) && ((1.0-cosine_similarities.get(index)) <=0.5)) { top10.add(index); }
                if (((1.0-cosine_similarities.get(index)) > 0.5) && ((1.0-cosine_similarities.get(index)) <=0.6)) { top10.add(index); }
                if (top10.size()>=10)
                    break;;
                index++;
            }

        }
        if (top10.size() < 10 ) {
            index=0;
            while (index<cosine_similarities.size()) {
                if (((1.0-cosine_similarities.get(index)) > 0.6) && ((1.0-cosine_similarities.get(index)) <=0.7)) { top10.add(index); }
                if (((1.0-cosine_similarities.get(index)) > 0.7) && ((1.0-cosine_similarities.get(index)) <=0.8)) { top10.add(index); }
                if (top10.size()>=10)
                    break;;
                index++;
            }

        }
        if (top10.size() < 10 ) {
            index=0;
            while (index<cosine_similarities.size()) {
                if (((1.0-cosine_similarities.get(index)) > 0.9) && ((1.0-cosine_similarities.get(index)) <=1.0)) { top10.add(index); }
                if (top10.size()>=10)
                    break;;
                index++;
            }

        }
        if (top10.size() < 10 ) {
            index=0;
            while (index<cosine_similarities.size()) {
                if (((1.0-cosine_similarities.get(index)) == NaN)) { top10.add(index); }
                if (top10.size()>=10)
                    break;;
                index++;
            }

        }

        //String user_choice_title = "Grand Theft Auto IV";
        String user_choice_title = "soulcalibur";
        String temp2;
        index=1;
        int game_match_counter = 0;
        Vector<String>list_view_output = new Vector<>();
        ArrayList<String> list_item;
        list_item = new ArrayList<>();


        //Displaying output in the screen
        while (index < 4)
        {
            temp2 = Integer.toString(index) + ". " + "Title: " + title.get(top10.get(index))+ ", Publisher: " + publisher.get(top10.get(index))+
                    ", Platform: " + platform.get(top10.get(index))+", Metacritic Rating: " +metascore.get(top10.get(index))+ ", User Rating: "+ avg_userscore.get(top10.get(index)) +""
                    +"\n"  ;
            list_view_output.add(temp2);
            list_item.add(temp2);
            index++;
        }


        ListAdapter theAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_item);
        ListView theListView = (ListView) findViewById(R.id.theListView);
        theListView.setAdapter(theAdapter);



        tf_idf.clear();
        cosine_similarities.clear();


    }

    public double tf(String doc1, String user_query_term) {
        StringTokenizer st1 = new StringTokenizer(doc1.toLowerCase());
        Vector<String> temp_query = new Vector<>();
        while (st1.hasMoreTokens()) {
            temp_query.add(st1.nextToken());
        }


        Double result = 0.0;

        int index = 0;
        while (index <temp_query.size())
        {
            if (user_query_term.equalsIgnoreCase(temp_query.get(index)))
                result++;
            index++;
        }
        if (result == 0)
            return 0.0;
        return (result/temp_query.size()) ;
    }

    public double idf(Vector<String>docs, String term) {
        double n = 0;
        int index = 0;
        while (index<docs.size())
        {
            StringTokenizer st1 = new StringTokenizer(docs.get(index).toLowerCase());
            Vector<String> temp_query = new Vector<>();
            while (st1.hasMoreTokens()) {
                temp_query.add(st1.nextToken());
            }

            int index3=0;
            while (index3 < temp_query.size()){
                if (term.equalsIgnoreCase(temp_query.get(index3))) {
                    n++;
                    break;
                }
                index3++;
            }

            index++;
        }
        if (n == 0)
            return 1.0;
        return (1+(Math.log(docs.size()) / n));
    }

    //Source: https://github.com/AdnanOquaish/Cosine-similarity-Tf-Idf-/blob/master/DocumentParser.java
    public double cosineSimilarity(Vector<Double>Vector1, Vector<Double> Vector2) {
        double docVector1[] = new double[Vector1.size()];
        for (int i = 0; i < Vector1.size(); i++) {
            docVector1[i] = Vector1.get(i);
        }
        double docVector2[] = new double[Vector2.size()];
        for (int i = 0; i < Vector2.size(); i++) {
            docVector2[i] = Vector2.get(i);
        }
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
        {
            dotProduct += docVector1[i] * docVector2[i];  //a.b
            magnitude1 += Math.pow(docVector1[i], 2);  //(a^2)
            magnitude2 += Math.pow(docVector2[i], 2); //(b^2)
        }

        magnitude1 = Math.sqrt(magnitude1);//sqrt(a^2)
        magnitude2 = Math.sqrt(magnitude2);//sqrt(b^2)

        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } else {
            return 0.0;
        }
        return cosineSimilarity;
    }
}
