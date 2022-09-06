package com.aniket.ayush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;

public class chatBot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        Kommunicate.init(chatBot.this,"1726319845d99fdd5d807106c0cd187d9");
        //Kommunicate.openConversation(this);
        Kommunicate.openConversation(chatBot.this, null, new KmCallback() {
            @Override
            public void onSuccess(Object message) {
//                Utils.printLog(MainActivity.this, "ChatTest", "Launch Success : " + message);
                Log.v("ChatTest", "Launch Success : " + message);
                new KmConversationBuilder(chatBot.this)
                        .setSingleConversation(true) // Pass false if you would like to create new conversation every time user starts a conversation. This is true by default which means only one conversation will open for the user every time the user starts a conversation.
                        .createConversation(new KmCallback() {
                            @Override
                            public void onSuccess(Object message) {
                                String conversationId = message.toString();
                                Log.d("ConversationTest",  conversationId);
                            }

                            @Override
                            public void onFailure(Object error) {
                                Log.d("ConversationTest", "Error : " + error);
                            }
                        });
            }

            @Override
            public void onFailure(Object error) {
//                Utils.printLog(MainActivity.this, "ChatTest", "Launch Failure : " + error);
                Log.v("ChatTest", "Launch Success : " + error);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}