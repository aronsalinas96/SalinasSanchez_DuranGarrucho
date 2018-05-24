package evolsoft.concesionario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.xml.bind.v2.runtime.SwaRefAdapter;

import evolsoft.concesionario.dto.CocheDTO;
import evolsoft.concesionario.exception.NotFoundExcept;
import evolsoft.concesionario.service.CocheService;

@RestController
@RequestMapping(value = "api/coche")
public class CocheController {
	
	@Autowired
	private CocheService cocheService;

	@RequestMapping(method = RequestMethod.GET)
	public List<CocheDTO> retrieveAll(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {
		return cocheService.findAll(page, size);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CocheDTO findOne(@PathVariable("id") Integer id) throws NotFoundExcept {
		return cocheService.findById(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public CocheDTO create(@RequestBody CocheDTO cocheDTO) {
		return cocheService.create(cocheDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable("id") Integer id, @RequestBody CocheDTO cocheDTO) {
		cocheService.update(id, cocheDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Integer id) {
		cocheService.delete(id);
	}

	@GetMapping("/sortedByPrice")
public List<CocheDTO> listCochesSortedByPrice(@RequestParam(required = false) Integer page,
@RequestParam(required = false) Integer size) {
return cocheService.listCochesSortedByPrice(page, size);
}
4
@GetMapping("/sold")
public List<CocheDTO> findCarsAlreadySold() {
return cocheService.findCarsAlreadySold();
}
@PutMapping("/sellCar")
public void sellCar(@RequestBody SoldCarDTO soldCarDTO) throws NotFoundExcept {
cocheService.newSell(soldCarDTO.getIdCoche(), soldCarDTO.getIdCliente(),
soldCarDTO.getIdVendedor());
}
@RequestMapping(value = "/inRange", method = RequestMethod.GET)
public List<CocheDTO> findCochesInPriceRange(@RequestParam(required = true) Integer min,
@RequestParam(required = true) Integer max) {
return (min < max) ?
cocheService.findCochesInPriceRange(min, max) :
cocheService.findCochesInPriceRange(max, min);
}
@RequestMapping(value = "/stock", method = RequestMethod.GET)
public List<CocheDTO> findCochesInStock() {
return cocheService.findCochesInStock();
}
@PostMapping("/insertList")
public void createList(@RequestBody List<CocheDTO> listCocheDto) {
cocheService.createList(listCocheDto);
}

//	@RequestMapping(value = "/{id}/sellCar", method = RequestMethod.GET)
//	public void sellCar(@PathVariable("id") Integer idCoche, @RequestParam(required = true) Integer idCliente,
//			@RequestParam(required = true) Integer idVendedor) throws NotFoundExcept {
//		cocheService.newSell(idCoche, idCliente, idVendedor);
//	}
//
//	@RequestMapping(value = "/inRange", method = RequestMethod.GET)
//	public List<CocheDTO> findCochesInPriceRange(@RequestParam(required = true) Integer min,
//			@RequestParam(required = true) Integer max) {
//		return (min < max) ? cocheService.findCochesInPriceRange(min, max) : cocheService.findCochesInPriceRange(max, min);
//	}
//
//	@RequestMapping(value = "/stock", method = RequestMethod.GET)
//	public List<CocheDTO> findCochesInStock() {
//		return cocheService.findCochesInStock();
//	}
	
}
