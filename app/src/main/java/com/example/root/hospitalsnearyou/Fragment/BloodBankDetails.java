package com.example.root.hospitalsnearyou.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.hospitalsnearyou.DB.HospitalDataBase;
import com.example.root.hospitalsnearyou.ModelClass.BloodBank;
import com.example.root.hospitalsnearyou.R;

import java.util.ArrayList;

public class BloodBankDetails extends Fragment {
    TextView pincode, email, website, contact, hospitalName, district, serviceTime, lattitude, city;
    TextView state, category, helpline, address, bloodComponent, bloodGroup, langitude, fax;
    BloodBank bloodBank;
    Bundle bundle = new Bundle();
    int position;
    String addressMap;
    ArrayList<BloodBank> arrayList = new ArrayList<>();
    HospitalDataBase dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blood_bank_details, container, false);
        position = getArguments().getInt("position");
        dbHelper = new HospitalDataBase(getActivity());
        String city = getArguments().getString("city");
        String state = getArguments().getString("state");
        arrayList.addAll(dbHelper.stateWiseHospitalForBloodBank(state,city));
        bloodBank = dbHelper.getSinglerecord(arrayList.get(position).getRowid());
        findId(rootView);
//        emailTextViewAction();
//        websiteTextViewAction();
//        addDialer();
        return rootView;
    }

    private void findId(View rootView) {
        email = (TextView) rootView.findViewById(R.id.EmailName);
        website = (TextView) rootView.findViewById(R.id.websiteName);
        hospitalName = (TextView) rootView.findViewById(R.id.HospitalName);
        state = (TextView) rootView.findViewById(R.id.StateName);
        city = (TextView) rootView.findViewById(R.id.Cityname);
        district = (TextView) rootView.findViewById(R.id.DistrictName);
        serviceTime = (TextView) rootView.findViewById(R.id.serviceTimeName);
        category = (TextView) rootView.findViewById(R.id.categoryName);
        pincode = (TextView) rootView.findViewById(R.id.pincodeName);
        contact = (TextView) rootView.findViewById(R.id.contactName);
        helpline = (TextView) rootView.findViewById(R.id.helpLineName);
        address = (TextView) rootView.findViewById(R.id.addressName);
        bloodComponent = (TextView) rootView.findViewById(R.id.bloodComponentName);
        bloodGroup = (TextView) rootView.findViewById(R.id.bloodGroupName);
        fax = (TextView) rootView.findViewById(R.id.faxName);
        setAllText();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Uri s = Uri.parse("geo:0,0?q=" + bloodBank.getHospitalName() + ", " + addressMap);
        Log.e("uri",""+s);
        if (addressMap != null) {
            showMap(s);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAllText() {
        hospitalName.setText(bloodBank.getHospitalName());
//        timestamp.setText(hospital.getTimestamp());
        state.setText(bloodBank.getState());
        city.setText(bloodBank.getCity());
        email.setText(bloodBank.getEmail());
        email.setTextSize(15);
        website.setText(bloodBank.getWebsite());
        bloodComponent.setText(bloodBank.getBloodComponent());
        bloodGroup.setText(bloodBank.getBloodGroup());
        address.setText(bloodBank.getAddress());
        addressMap = bloodBank.getAddress();
        pincode.setText(bloodBank.getPincode());
        fax.setText(bloodBank.getFax());
        helpline.setText(bloodBank.getHelpline());
        contact.setText(bloodBank.getContact());
        category.setText(bloodBank.getCategory());
        district.setText(bloodBank.getDistrict());
        serviceTime.setText(bloodBank.getServiceTime());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.map);
        menuItem.setVisible(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

