package org.theberlins.citest;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;

@RestController

public class ImageNumberRecognizer {

	private EasyPredictModelWrapper model = null;

	private ImageNumberRecognizer() throws IOException {
		String fileName = "deeplearning.zip";
		model = new EasyPredictModelWrapper(MojoModel.load(fileName));
	}

	@RequestMapping(value = "/recognizeNumber", method = RequestMethod.POST)
	public ImageRecognitionResult recognize(@RequestBody String request) throws Exception {

		RowData row = new RowData();

		JSONParser parser = new JSONParser();

		JSONObject object = (JSONObject) parser.parse(request);

		// JSONObject doesn't support type parameters
		Set<Object> keys = object.keySet();
		Iterator<Object> iterator = keys.iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			row.put(key.toString(), object.get(key).toString());
		}

		MultinomialModelPrediction p = model.predictMultinomial(row);

		return new ImageRecognitionResult("SUCCESS", p.label, p.classProbabilities[p.labelIndex]);
	}
}
