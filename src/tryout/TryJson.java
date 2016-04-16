package tryout;

import java.lang.reflect.Type;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import SimpleDTO;

public class TryJson {

	public static void main(String[] args) {
		
		SimpleDTO dto = new SimpleDTO();
		
		Gson gObj = new Gson();
		String strJson = gObj.toJson(dto);
		
		Gson gson = new GsonBuilder()
        .disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setPrettyPrinting()
        .serializeNulls()
        .create();
		
		dto.ix = 34;
		
		String strJson2 = gson.toJson(dto);
		
		
		System.out.println("Simple" + strJson);
		System.out.println("Simple 2" + strJson2);
		
		SimpleDTO dto2 = gson.fromJson(strJson, SimpleDTO.class);  
		System.out.println("DTO2: " + dto2.toString());

		dto2.setIX(56);
		
		System.out.println("DTO2: " + dto2.toString());

	}

}
