package org.theberlins.citest;

import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;

@RestController

public class ImageNumberRecognizer {

	private  EasyPredictModelWrapper model = null;
	private ImageNumberRecognizer() throws IOException{
		String fileName = "deeplearning.zip";
		model = new EasyPredictModelWrapper(MojoModel.load(fileName));
	}
	
	@RequestMapping("/recognizeNumber")
	public int recognize(Vector<Integer> pixels) throws Exception {

		RowData row = new RowData();
		
		for(int i = 0 ; i < pixels.size() - 1; i++ )
		{
			row.put("C"+ (i + 1), pixels.elementAt(i).toString());
		}
		
		MultinomialModelPrediction p = model.predictMultinomial(row);
		
		return Integer.parseInt(p.label);
	}
}
