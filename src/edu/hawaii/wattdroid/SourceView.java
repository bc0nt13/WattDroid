package edu.hawaii.wattdroid;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;

/**
 * Sourceview - displays sources in a list context. Uses the list adaptor and presents list of xml
 * sources from a wattdepot server.
 * 
 */
public class SourceView extends Activity {

  /**
   * Debug tag for log writing.
   */
  private final String MY_DEBUG_TAG = "wattdroid";
  private String source = null;
  private TextView tv;
  /** Need handler for call-backs to the UI thread **/
  private RefreshHandler mRedrawHandler = new RefreshHandler();

  /**
   * Inner class of Sourceview thats purpose is to handle messages from threads and act upon them,
   * ie calling updateUI
   */
  class RefreshHandler extends Handler {
    /**
     * handleMessage - This is called in order to update the UI in a thread.
     * 
     * @param msg - an empty message that triggers the update
     */
    @Override
    public void handleMessage(Message msg) {
      SourceView.this.updateUI();
    }

    /**
     * Delays refresh for the param amount of time in ms.
     * 
     * @param delayMillis - num of ms to delay
     */
    public void sleep(long delayMillis) {
      this.removeMessages(0);
      if (!isFinishing()) {
        sendMessageDelayed(obtainMessage(0), delayMillis);
      }
    }
  };

  /**
   * updateUI - The background Thread that processes new XML information to display in the textview.
   */
  private void updateUI() {

    try {
      /* Create a URL we want to load some xml-data from. */
      URL url = new URL("http://server.wattdepot.org:8182/wattdepot/sources/" + source);

      /* Get a SAXParser from the SAXPArserFactory. */
      SAXParserFactory spf = SAXParserFactory.newInstance();
      SAXParser sp = spf.newSAXParser();

      /* Get the XMLReader of the SAXParser we created. */
      XMLReader xr = sp.getXMLReader();
      /* Create a new ContentHandler and apply it to the XML-Reader */
      ExampleHandler myExampleHandler = new ExampleHandler();
      xr.setContentHandler(myExampleHandler);

      /* Parse the xml-data from our URL. */
      xr.parse(new InputSource(url.openStream()));

      /* Our ExampleHandler now provides the parsed data to us. */
      ParsedExampleDataSet parsedExampleDataSet = myExampleHandler.getParsedData();

      /* Set the result to be displayed in our GUI. Removed for testing */
      tv.setText(parsedExampleDataSet.toString());
      Log.d("wattdroid", "Displaying Source XML");
    }
    catch (Exception e) {
      /* Display any Error to the GUI. */
      tv.setText("Whoops! WattDroid made a booboo!: " + e.getMessage());
      Log.e(MY_DEBUG_TAG, "wattdroid", e);
    }
    mRedrawHandler.sleep(10000);
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.sources);
    this.tv = (TextView) this.findViewById(R.id.sourcetext);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      source = extras.getString("source");
    }
    updateUI();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

}
