package activity.com.businessstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.businessstore.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterMyMessageRecycler;

public class MyMessageActivity extends BaseActivity implements View.OnClickListener {
    private List<String> urlList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);

        initdata();
        initview();
    }


    private void initdata() {
        urlList=new ArrayList<>();
    }

    private void initview() {
        setTitleView(R.drawable.backimage,R.string.my_message);

        recyclerView=findViewById(R.id.comment_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        AdapterMyMessageRecycler adapterMyMessageRecycler=new AdapterMyMessageRecycler(getApplicationContext(),urlList);
        adapterMyMessageRecycler.setmOnItemClickListener(new AdapterMyMessageRecycler.OnItemReplyClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent replyIntent=new Intent(MyMessageActivity.this,ReplyActivity.class);
                startActivity(replyIntent);
            }
        });
        recyclerView.setAdapter(adapterMyMessageRecycler);
    }

    @Override
    public void onClick(View v) {

    }
}
