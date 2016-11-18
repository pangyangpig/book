package com.example.arthur.sampleapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.arthur.sampleapp.R;
import com.example.arthur.sampleapp.bean.Movies;
import com.example.arthur.sampleapp.bean.Subject;
import com.example.arthur.sampleapp.network.HttpMethods;
import com.example.arthur.sampleapp.network.ProgressDialogSubscriber;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = (TextView) findViewById(R.id.tv_display);
        findViewById(R.id.btn_test).setOnClickListener(this);
    }

    public void onClick(View view){
        getMovies();
    }

    private void getMovies(){
        ProgressDialogSubscriber<Movies> nextListener = new ProgressDialogSubscriber<Movies>(this) {
            @Override
            public void onNext(Movies movieEntity) {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(movieEntity.getTitle()).append("\n");
                for (Subject subject : movieEntity.getSubjects()){
                    stringBuilder.append(subject.getTitle()).append("\n");
                }

                tvDisplay.setText(stringBuilder.toString());
            }
        };

        HttpMethods.getMovies(nextListener, 0, 10);

    }

}
