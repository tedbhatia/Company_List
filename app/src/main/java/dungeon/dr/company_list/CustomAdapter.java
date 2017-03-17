package dungeon.dr.company_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 14-Dec-16.
 */
public class CustomAdapter extends ArrayAdapter {
    public CustomAdapter(Context context, List<String> test) {
        super(context,R.layout.custom_row, test);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ayu_layoutInflater = LayoutInflater.from(getContext());
        View custom_view = ayu_layoutInflater.inflate(R.layout.custom_row, parent, false);

        String single_text = (String) getItem(position);
        TextView ayu_text = (TextView) custom_view.findViewById(R.id.text);
        ImageView ayu_image = (ImageView) custom_view.findViewById(R.id.image);

        /*ayu_image.setImageResource(image url);*/
        ayu_text.setText(single_text);

        return custom_view;
    }
}
