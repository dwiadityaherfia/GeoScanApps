package laurens.geo.scanner;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.Button;

//import android.content.*;
//import android.widget.*;

public class MenuActivity extends Activity {

	WebView wvMenuActivity;
	JavaScriptInterface JSInterface; 
	Dialog dialogExit;
	Button btnExitYa,btnExitTidak;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menuactivity);
		wvMenuActivity = (WebView)findViewById(R.id.wvMenuActivity);
		wvMenuActivity.getSettings().setJavaScriptEnabled(true);
		wvMenuActivity.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		wvMenuActivity.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url){}
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){}
        });
		JSInterface = new JavaScriptInterface(this);
		wvMenuActivity.addJavascriptInterface(JSInterface, "JSInterface");
		wvMenuActivity.loadUrl("file:///android_asset/apps/public_html/menu.html");
	}
	
	
	public class JavaScriptInterface {
	    Context mContext;

	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }

	    public void menuMenu(){
	    	wvMenuActivity.loadUrl("file:///android_asset/apps/public_html/menu.html");
	    }
	    
	    public void menuScan(){
	    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
	    }
	    
	    public void menuPetunjuk(){
	    	wvMenuActivity.loadUrl("file:///android_asset/apps/public_html/petunjuk.html");
	    }

	    public void menuPengembang(){
	    	wvMenuActivity.loadUrl("file:///android_asset/apps/public_html/tentang.html");
	    }
	    
	    public void menuKeluar(){
	    	dialogExit = new Dialog(MenuActivity.this);
			dialogExit.setContentView(R.layout.activity_dialogexit);
			dialogExit.setTitle("Konfirmasi Keluar");
			btnExitYa = (Button) dialogExit.findViewById(R.id.btnExitYa);
			btnExitTidak = (Button) dialogExit.findViewById(R.id.btnExitTidak);
			dialogExit.show();
			
			btnExitYa.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			
			btnExitTidak.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialogExit.dismiss();
				}
			});
	    }
	    
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent){     
		if(requestCode == 0){
			if(resultCode == RESULT_OK){
				String contents = intent.getStringExtra("SCAN_RESULT");  
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");              
				String yy = "Data yang diperoleh adalah " + contents + " format : " + format;
				Log.i("xZing", yy);              // Handle successful scan     
				//Toast.makeText(getApplicationContext(), yy , Toast.LENGTH_SHORT).show();
				finish();
				Intent a = new Intent(getApplicationContext(),ScanResult.class);
				a.putExtra("code", contents);
				startActivity(a);
			}else
			if(resultCode == RESULT_CANCELED){              
				// Handle cancel             
				Log.i("xZing", "Cancelled"); 
				//Toast.makeText(getApplicationContext(), yy, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public void onBackPressed(){
		JSInterface.menuKeluar();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
