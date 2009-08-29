package tesis.parser;

import encoder.DataEncoder;
import encoder.FilterCategoryEncoder;
import filter.CategoryDataFilter;

/**
 * Filter the category that have > 5 instances. Add only the instances that
 * belong to those categories.
 * 
 * @author nicotourne
 * 
 */
public class ArffConverterImpl extends ArffConverter {

	public ArffConverterImpl() {
		super();
	}

	public void iniEncoders() {
		categoryEncoder = new FilterCategoryEncoder();
		queryEncoder = new DataEncoder();
		anchorTextEncoder = new DataEncoder();
		tagEncoder = new DataEncoder();
	}

	/**
	 * Write data in the ARFF file
	 * 
	 */
	public void writeARFFData() {
		// Filter the categories
		this.setDataFilter(new CategoryDataFilter(this.categoryEncoder
				.getCategories()));
		super.writeARFFData();
	}

}
