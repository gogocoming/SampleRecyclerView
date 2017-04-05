package com.example.apple.test47;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private String[] arr1={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private List<String> mDatas;
    private MyAdapter myAdapter;

    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDatas=new ArrayList<String>(Arrays.asList(arr1));

        mRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerview);
        //创建线性LayoutManager
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        //创建grid LayoutManager
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        //mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));

        //固定每个item的高度，可提高性能
        //mRecyclerView.setHasFixedSize(true);
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //创建并设置Adapter
        myAdapter = new MyAdapter();
        myAdapter.setOnItemClickLitener(new OnMyItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击事件触发", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "长按事件触发", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu1:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.menu2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
                break;
            case R.id.menu3:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
                break;
            case R.id.menu4:
                myAdapter.addData(1);
                break;
            case R.id.menu5:
                myAdapter.removeData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        //创建新View，被LayoutManager所调用
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        private OnMyItemClickLitener mOnMyItemClickLitener;
        public void setOnItemClickLitener(OnMyItemClickLitener mOnMyItemClickLitener)
        {
            this.mOnMyItemClickLitener = mOnMyItemClickLitener;
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
            myViewHolder.mTextView.setText(mDatas.get(position));
            //设置瀑布流，高度为随机数
            ViewGroup.LayoutParams lp=myViewHolder.mTextView.getLayoutParams();
            lp.height=getRandom();
            myViewHolder.mTextView.setLayoutParams(lp);




            // 如果设置了回调，则设置点击事件
            if (mOnMyItemClickLitener != null)
            {
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = myViewHolder.getLayoutPosition();
                        mOnMyItemClickLitener.onItemClick(myViewHolder.itemView, pos);
                    }
                });

                myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        int pos = myViewHolder.getLayoutPosition();
                        mOnMyItemClickLitener.onItemLongClick(myViewHolder.itemView, pos);
                        return false;
                    }
                });
            }

        }
        //获取数据的数量
        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public  class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public MyViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text);
            }
        }

        private int getRandom() {
            Random random = new Random();

            int number = 0;
            while(number<40)
                number=random.nextInt(200);

            return number;
        }

        public void addData(int position) {
            mDatas.add(position, "Insert One");
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }




    }

    public interface OnMyItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }


}
