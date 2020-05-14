package com.example.app.userScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.app.R;

public class Groups extends Fragment {

    private static Groups INSTANCE = null;

    View view;

    public Groups(){
    }

    public static Groups getINSTANCE(){

        if (INSTANCE == null)
            INSTANCE = new Groups();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private WebView chat;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.groups_fragment,container,false);

        chat = (WebView) view.findViewById(R.id.webview);
        chat.setWebViewClient(new WebViewClient());
        String login = "";
        if(MainScreen.getMail().equals("-null-"))
            login = MainScreen.getNrtel();
        else
            login = MainScreen.getMail();
        chat.loadUrl("http://www.gladiaholdings.com/PHP/chat.php?login=" + login + "&pass=" + MainScreen.getPassword());

        return view;
    }
}
