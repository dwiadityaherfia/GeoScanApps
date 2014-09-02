package laurens.geo.scanner;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
//import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.Button;

//import android.content.*;
//import android.widget.*;

public class ScanResult extends Activity {

	WebView wvScanResult;
	JavaScriptInterface JSInterface; 
	Dialog dialogExit;
	Button btnExitYa,btnExitTidak;
	
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_scanresult);		
		Intent j = getIntent();
		String k = j.getStringExtra("code");
		//final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...",false);
		wvScanResult = (WebView)findViewById(R.id.wvScanResult);
		wvScanResult.getSettings().setJavaScriptEnabled(true);
		wvScanResult.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		wvScanResult.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url){
//				if(pd.isShowing()&&pd!=null){
//                	pd.dismiss();
//                }
			}
			
			@Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            	wvScanResult.loadUrl("file:///android_asset/apps/public_html/error.html");
            }
        });
		JSInterface = new JavaScriptInterface(this);
		wvScanResult.addJavascriptInterface(JSInterface, "JSInterface");
		wvScanResult.loadUrl("http://e-laurensius.com/barcode/trap.php?code="+k);
	}
	
	
	public class JavaScriptInterface {
	    Context mContext;

	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }

	    public void menuMenu(){
	    	//wvScanResult.loadUrl("file:///android_asset/apps/public_html/menu.html");
	    	finish();
	    	Intent i = new Intent(ScanResult.this,MenuActivity.class);
	    	startActivity(i);
	    }
	    
	    public void menuPetunjuk(){
	    	wvScanResult.loadUrl("file:///android_asset/apps/public_html/petunjuk.html");
	    }

	    public void menuPengembang(){
	    	wvScanResult.loadUrl("file:///android_asset/apps/public_html/tentang.html");
	    }
	    
	    public void menuKeluar(){
	    	dialogExit = new Dialog(ScanResult.this);
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
