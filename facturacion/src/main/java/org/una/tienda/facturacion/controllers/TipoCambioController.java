package org.una.tienda.facturacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Esteban Vargas
 */
@Controller
public class TipoCambioController {
    double conversionDolaresAColones= 610;
    
	@RequestMapping("/tipo-cambio/a-dolares/{colones}")
	public @ResponseBody String cambioADollar(@PathVariable(value = "colones") String x) {
            double calculo = Double.parseDouble(x);
		return calculo/conversionDolaresAColones+"";
	}
}
