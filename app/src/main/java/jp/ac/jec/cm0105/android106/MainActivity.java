package jp.ac.jec.cm0105.android106;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int MENU_YAHOO = 0;
    private static final int MENU_GOOGLE = 1;
    private static final int MENU_END = 2;
    private WebView wView;//下方空白区域
    private Button btnLoad;//右上读入按钮
    private TextView edtURL;//输入框
    private Button btnCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        wView = findViewById(R.id.wView);       //下方空白页区域ID
        wView.setWebViewClient(new WebViewClient());
        wView.getSettings().setJavaScriptEnabled(true);
        wView.getSettings().setBuiltInZoomControls(true);//放缩控制大小
        wView.loadUrl("https://news.yahoo.co.jp/");//空白区显示雅虎界面

        edtURL = findViewById(R.id.edtURL);       //输入框ID
        btnCheck=findViewById(R.id.cbJsEnable);

//确定按钮👇
        btnLoad = findViewById(R.id.btnLoad);       //确定按钮ID
        //确定按钮监视器
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CheckBox cbx=findViewById(R.id.cbJsEnable);
                if(cbx.isChecked()){
                    wView.getSettings().setJavaScriptEnabled(true);
                }else{
                    wView.getSettings().setJavaScriptEnabled(false);
                }

                String strURL = edtURL.getText().toString();//输入框 视图文本-->String
                //判断输入是否为空
                if (!TextUtils.isEmpty(strURL)) {
                    wView.loadUrl("https://"+strURL);//空白区显示接收输入框的变量界面
                } else {
                     dialogWindow();
                    Toast.makeText(MainActivity.this, "URLを入力して!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(wView.canGoBack()){
                    wView.goBack();
                }
                else{
                    finish();
                }
            }
        });

    }//onCreat end


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_YAHOO, Menu.NONE, getString(R.string.yahoo));
        menu.add(Menu.NONE, MENU_GOOGLE, Menu.NONE, getString(R.string.google));
        menu.add(Menu.NONE, MENU_END, Menu.NONE, getString(R.string.finish));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_YAHOO:
                wView.loadUrl("https://news.yahoo.co.jp/");//空白区显示跳转雅虎界面
                Toast.makeText(this, "Yahooを表示する？", Toast.LENGTH_SHORT).show();//弹窗
                return true;
            case MENU_GOOGLE:
                wView.loadUrl("https://www.google.com/");//空白区显示跳转谷歌界面
                Toast googletoast = Toast.makeText(this, "Googleを表示する？", Toast.LENGTH_SHORT);//弹窗
                googletoast.show();
                return true;
            case MENU_END:
                finish();
                return true;
            default:
        }
        return false;
    }

        private void dialogWindow(){
        AlertDialog dialog= new AlertDialog.Builder(this)
                .setTitle("URLを入力して！")     //设置弹窗标题
                //普通按钮*2
                .setNegativeButton("Googleを表示する", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wView.loadUrl("https://www.google.com/");//空白区显示谷歌界面
                    }
                })
                .setNeutralButton("Adobeを表示する", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wView.loadUrl("https://www.adobe.com/");//空白区显示adobe界面
                    }
                })
                //ok按钮
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "URLを入力して!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();//销毁弹窗
                    }
                })
                .create();   //用create方法创建弹窗

        dialog.show();
    }


}