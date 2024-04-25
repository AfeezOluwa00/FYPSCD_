package com.sickle.healthcareapp.model.medicineNameModels;

import java.util.ArrayList;

public class Result{
    public String product_ndc;
    public String generic_name;
    public String labeler_name;
    public String brand_name;
    public ArrayList<ActiveIngredient> active_ingredients;
    public boolean finished;
    public ArrayList<Packaging> packaging;
    public String listing_expiration_date;
    public Openfda openfda;
    public String marketing_category;
    public String dosage_form;
    public String spl_id;
    public String product_type;
    public ArrayList<String> route;
    public String marketing_start_date;
    public String product_id;
    public String application_number;
    public String brand_name_base;
    public int skip;
    public int limit;
    public int total;
}
