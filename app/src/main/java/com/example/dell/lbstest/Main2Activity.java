package com.example.dell.lbstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();

    private RecyclerView recyclerView;

    private EditText editText;

    private Button button_send;

    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initMsg();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        editText = (EditText)findViewById(R.id.edit_text);
        button_send = (Button)findViewById(R.id.button_send);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        msgAdapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(msgAdapter);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                if(!"".equals(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    recyclerView.scrollToPosition(msgList.size()-1);
                    editText.setText("");
                    Msg msgResponse = new Msg("Hi,自动回复",Msg.TYPE_RECEIVE);
                    msgList.add(msgResponse);
                }
            }
        });
    }

    private void initMsg() {
        Msg msg1 = new Msg("Hello,this is Xinyun Zhang.Nice to meet you",Msg.TYPE_RECEIVE);
        msgList.add(msg1);

        Msg msg2 = new Msg("Hi",Msg.TYPE_SEND);
        msgList.add(msg2);
    }
}
