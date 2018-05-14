package com.expat.com.expat_app_v02;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slider_images = {
        R.drawable.one,
        R.drawable.two,
        R.drawable.three,
        R.drawable.four
    };

    public String[] slider_headings = {
            "1",
            "2",
            "3",
            "4"
    };

    public String[] slider_descr = {
            "1 Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Maecenas pharetra arcu nec nulla accumsan ultricies. " +
                    "Maecenas sit amet justo eu sem pretium finibus. " +
                    "Pellentesque pretium lacinia magna a euismod. Proin convallis, turpis non sodales dictum, d" +
                    "olor enim cursus enim, eu malesuada nibh elit at ante. Vivamus tincidunt lacus lectus, vel aliquam metus tincidunt quis",
            "2 Mauris quis risus tincidunt, commodo massa eget, ullamcorper velit. " +
                    "Nam cursus lacus a risus lobortis tempor. Vivamus ut erat vel magna ultricies aliquet. " +
                    "Donec purus mi, tincidunt vitae viverra at, convallis id ex. " +
                    "Donec sed sem ultrices, placerat est ut, laoreet dolor.",
            "3 Donec quis suscipit diam. Curabitur nec enim mattis, interdum elit in, rutrum leo. " +
                    "Nullam eu posuere dolor, in tristique ex. Donec sed venenatis lacus. ",
            "4 felis, eu suscipit dolor ultrices sit amet. Maecenas lectus purus, " +
                    "posuere quis blandit ut, ornare sit amet ex. Class aptent taciti sociosqu ad lito"
    };

    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.slideRelativeLayout);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slideImageView);
        TextView slideHeading = (TextView) view.findViewById(R.id.slideTextView);
        TextView slideDesc = (TextView) view.findViewById(R.id.slideDescView);

        slideImageView.setImageResource(slider_images[position]);
        slideHeading.setText(slider_headings[position]);
        slideDesc.setText(slider_descr[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
