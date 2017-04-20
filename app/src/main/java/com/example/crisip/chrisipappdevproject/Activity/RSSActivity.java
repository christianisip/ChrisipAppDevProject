package com.example.crisip.chrisipappdevproject.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.crisip.chrisipappdevproject.R;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RSSActivity extends AppCompatActivity {

    private ListView lv;
    private  String FEED_URL = "http://www.winnipegfreepress.com/rss/?path=%2Flocal";
    private  String BLUE_BOMBERS_URL = "http://www.winnipegfreepress.com/rss/?path=%2Fsports%2Ffootball%2Fbombers&path=%2Fsports%2Ffootball%2Fbombers";
    private URL feedURL;
    private BufferedReader bufferedReader;
    private ArrayList<ListItem> lvItemRSS;

    @Override
    protected void onResume()
    {
        super.onResume();
        AsyncTest asyncTest = new AsyncTest();
        asyncTest.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
    }

    class rssListAdapter extends ArrayAdapter<ListItem>
    {
        private ArrayList<ListItem> rssOutput;
        public rssListAdapter(Context context, int textViewResourceId, ArrayList<ListItem> rssOutput) {
            super(context, textViewResourceId, rssOutput);
            this.rssOutput = rssOutput;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            if (view == null)
            {
                LayoutInflater layoutinflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutinflater.inflate(R.layout.listitem_rss, null);
            }
            if (rssOutput.get(position) != null)
            {
                TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
                TextView tvTitle = (TextView) view.findViewById(R.id.tvTtitle);
                TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);

                if (tvTitle != null) {
                    tvTitle.setText(rssOutput.get(position).getTitle());
                    tvTitle.setTextSize(16);
                    tvTitle.setAllCaps(false);
                    tvTitle.setTextColor(Color.RED);

                }
                if (tvDate != null) {
                    tvDate.setText("Publish Date: " + rssOutput.get(position).getPubDate());
                    tvDate.setBackgroundColor(Color.BLACK);
                }
                if (tvDescription != null)
                {
                    tvDescription.setText(rssOutput.get(position).getDescription().substring(0, 100) + "...");
                    tvDescription.setHeight(150);
                    tvDescription.setTextSize(16);
                    tvDescription.setTextColor(Color.WHITE);
                }
            }
            return view;
        }
    }

    class FreepHandler extends DefaultHandler {

        private boolean inTitle, inDesc, inLink, inPub, inItem;
        private ListItem rsslistItem;
        StringBuilder stringBuilder;

        public FreepHandler()
        {
            lvItemRSS = new ArrayList<>();
        }

        @Override
        public void startDocument() throws SAXException
        {
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException
        {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            if(qName.equals("item"))
            {
                inItem = true;
                rsslistItem = new ListItem();
            }

            if(inItem)
            {
                if (qName.equals("title")) {
                    inTitle = true;
                    stringBuilder = new StringBuilder(255);

                }
                else if (qName.equals("description")) {
                    inDesc = true;
                    stringBuilder = new StringBuilder(255);

                }
                else if (qName.equals("pubDate")) {
                    inPub = true;
                    stringBuilder = new StringBuilder(255);

                }
                else if (qName.equals("link")) {
                    inLink  = true;
                    stringBuilder = new StringBuilder(255);

                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            if(qName.equals("item")) {
                inItem = false;
                lvItemRSS.add(rsslistItem);
            }

            if(inItem) {
                if (qName.equals("title")) {
                    inTitle = false;
                    rsslistItem.setTitle(stringBuilder.toString());
                }

                else if (qName.equals("description")) {
                    inDesc = false;
                    rsslistItem.setDescription(stringBuilder.toString());
                }
                else if (qName.equals("pubDate")) {
                    inPub = false;
                    rsslistItem.setPubDate(stringBuilder.toString());
                }
                else if (qName.equals("link")) {
                    inLink = false;
                    rsslistItem.setLink(stringBuilder.toString());
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            String s = new String(ch, start, length);
            if(inTitle)
            {
                stringBuilder.append(ch, start, length);
            }

            if(inDesc)
            {
                stringBuilder.append(ch, start, length);
            }
            if(inPub)
            {
                stringBuilder.append(ch, start, length);
            }
            if(inLink)
            {
                stringBuilder.append(ch, start, length);
            }
        }
    }


    class AsyncTest extends AsyncTask
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try
            {

                SAXParserFactory spf = SAXParserFactory.newInstance();
                feedURL = new URL(BLUE_BOMBERS_URL);
                SAXParser saxParser = spf.newSAXParser();
                FreepHandler freepHandler = new FreepHandler();
                bufferedReader = new BufferedReader(
                        new InputStreamReader(feedURL.openStream()));

                saxParser.parse(new InputSource(bufferedReader), freepHandler);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object object)
        {
            super.onPostExecute(object);

            rssListAdapter customListAdapter = new rssListAdapter(RSSActivity.this, R.layout.listitem_rss, lvItemRSS);

            ListView lv = (ListView)findViewById(R.id.lvRSS);
            customListAdapter.notifyDataSetChanged();
            lv.setAdapter(customListAdapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(RSSActivity.this, RssDetailActivity.class);
                    intent.putExtra("passDescription", lvItemRSS.get(position).getDescription());
                    intent.putExtra("passLink", lvItemRSS.get(position).getLink());
                    startActivity(intent);
                }
            });

        }
    }

    class ListItem implements Serializable
    {
        private String title;
        private String description;
        private String pubDate;
        private String link;

        public ListItem(){
            title = "";
            description = "";
            pubDate = "";
            link = "";
        }


        public String getTitle(){
            return title;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public String getDescription(){
            return description;
        }

        public void setDescription(String description){
            this.description = description;
        }

        public String getPubDate(){
            return pubDate;
        }

        public void setPubDate(String pubDate){
            this.pubDate = pubDate;
        }

        public String getLink(){
            return link;
        }

        public void setLink(String link){
            this.link = link;
        }
    }
}

