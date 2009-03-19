package tesis.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.SAXException;

import encoder.FilterCategoryEncoder;
import encoder.QueryEncoder;
import filter.CategoryDataFilter;

/**
 * Filter the category that have > 5 instances. Add only the instances that
 * belong to those categories.
 * 
 * @author nicotourne
 * 
 */
public class CategoryFilterArffConverter extends ArffConverter {

	public CategoryFilterArffConverter() {
		super();
	}

	public void iniEncoders() {
		categoryEncoder = new FilterCategoryEncoder();
		queryEncoder = new QueryEncoder();
	}


	/**
	 * Write data in the ARFF file 
	 *
	 */
	public void writeARFFData() {
		this.setDataFilter(new CategoryDataFilter(this.categoryEncoder.getCategories()));
		super.writeARFFData();
	}
	
}
