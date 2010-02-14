package tesis.parser;

import encoder.DataEncoder;
import encoder.FilterCategoryEncoder;
import encoder.TermsEncoder;
import filter.CategoryDataFilter;
import filter.TermsFilter;

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
		termsEncoder = new TermsEncoder();
	}

	/**
	 * Write data in the ARFF file
	 * 
	 */
	public void writeARFFData() {
		// Filter the categories
		this.setDataFilter(new CategoryDataFilter(this.categoryEncoder
				.getCategories()));
		this.setTermsFilter(new TermsFilter(this.termsEncoder));		
		super.writeARFFData();
	}

}
