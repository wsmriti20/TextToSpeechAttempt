package com.example.texttospeechattempt02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TTSListener{
    EditText inputText;
    Button convertButton, clearButton;
    private TextView speechText;
    private TextToSpeech OutputSpeechText;
    TextToSpeech textToSpeech;
    TTSListener tts = new TTSListener()
    {
        @Override
        public void speak(final String text) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Speak1","This is working 1");
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            });
        }

        @Override
        public void pause(long duration) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.input_text);
        clearButton = findViewById(R.id.clearBtn);
        convertButton = findViewById(R.id.convertBtn);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

//        convertButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String input = inputText.getText().toString();
//                Log.d("input", input);
//                tts.speak(input);
//            }
//        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setText("");
            }
        });
    }

    public void getSpeechInput(View view) {

        Intent intent  = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,10);
        }
        else{
            Toast.makeText(this,"Your device doesn't support speech input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    inputText.setText(result.get(0));
                    if(result.contains("hey")){
                        inputText.setText("YOLO");
                        CharSequence text =  "These are the incentives";
                        tts.speak("YOLO");
                        Log.d("Speeech", "YOLOO");
                        Bundle params = new Bundle();
                        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");

//                        String text = editText.getText().toString();
//                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Dummy String");
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            OutputSpeechText.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Dummy String");
//                            Log.d("InSpeech", "IDK");
//                        }

                    }
                }
                break;
        }
    }

    @Override
    public void speak(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("Speak2","This is working 2");
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    public void pause(long duration) {

    }


}
