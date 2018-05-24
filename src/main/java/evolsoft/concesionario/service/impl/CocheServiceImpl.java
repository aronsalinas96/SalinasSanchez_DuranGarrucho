package evolsoft.concesionario.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import evolsoft.concesionario.configuration.PageReqConfig;
import evolsoft.concesionario.dao.CocheDAO;
import evolsoft.concesionario.dto.ClienteDTO;
import evolsoft.concesionario.dto.CocheDTO;
import evolsoft.concesionario.dto.VendedorDTO;
import evolsoft.concesionario.exception.NotFoundExcept;
import evolsoft.concesionario.model.Coche;
import evolsoft.concesionario.service.ClienteService;
import evolsoft.concesionario.service.CocheService;
import evolsoft.concesionario.service.VendedorService;

@Service
public class CocheServiceImpl implements CocheService {

	@Autowired
	private CocheDAO cocheDAO;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private VendedorService vendedorService;

	@Autowired
	private DozerBeanMapper dozer;

	@Override
	public CocheDTO findById(Integer id) throws NotFoundExcept {
		final Coche coche = Optional.ofNullable(cocheDAO.findOne(id))
			.orElseThrow(() -> new NotFoundExcept("Coche con id " + id + " no encontrado"));
		Coche soldCar = Optional.ofNullable(cocheDAO.findOne(idCoche)).orElseThrow(() -> new NotFoundExcept());
		return map(coche);
	}
	@Override
	public void createList(List<CocheDTO> listCocheDto) {
	listCocheDto.forEach(cocheDTO -> cocheDAO.save(map(cocheDTO)));
	}

	@Override
	public List<CocheDTO> findAll(Integer page, Integer size) {
		final Iterable<Coche> allCoches = cocheDAO.findAll(PageReqConfig.newPageRequest(page, size));
		final List<CocheDTO> coches = new ArrayList<>();
		allCoches.forEach(coche -> {
			final CocheDTO cocheDTO = map(coche);
			coches.add(cocheDTO);
		});
		return coches;
	}

	@Override
	public CocheDTO create(CocheDTO cocheDTO) {
		final Coche coche = map(cocheDTO);
		return map(cocheDAO.save(coche));
	}

	@Override
	public void update(Integer id, CocheDTO cocheDTO) {
		final Coche coche = map(cocheDTO);
		coche.setId(id);
		cocheDAO.save(coche);
	}

	@Override
	public void delete(Integer idCoche) {
		cocheDAO.delete(idCoche);
	}

	@Override
	public Coche map(CocheDTO cocheDTO) {
		return dozer.map(cocheDTO, Coche.class);
	}

	@Override
	public CocheDTO map(Coche coche) {
		return dozer.map(coche, CocheDTO.class);
	}

	@Override
	public List<CocheDTO> listCochesSortedByPrice(Integer page, Integer size) {
		final Iterable<Coche> allCoches = cocheDAO.listCarsSortedByPrice();
		final List<CocheDTO> coches = new ArrayList<>();
		allCoches.forEach(coche -> {
			final CocheDTO cocheDTO = map(coche);
			coches.add(cocheDTO);
		});
		return coches;
	}

	@Override
	public List<CocheDTO> findCarsAlreadySold() {
		List<Coche> coches = cocheDAO.findCarsAlreadySold();
		final List<CocheDTO> cochesInStock = new ArrayList<>();
		coches.forEach(coche -> {
			final CocheDTO cocheDTO = map(coche);
			cochesInStock.add(cocheDTO);
		});
		return cochesInStock;
	}

	@Override
	public List<CocheDTO> findCochesInPriceRange(Integer minPrice, Integer maxPrice)
	{
		List<Coche> coches = cocheDAO.findCarsBtwPriceRange(minPrice, maxPrice);
		final List<CocheDTO> cochesInRange = new ArrayList<>();
		coches.forEach(coche -> {
			final CocheDTO retrievedCar = map(coche);
			cochesInRange.add(cocheDTO);
			});
		return cochesInRange;
	}

	@Override
	public List<CocheDTO> findCochesInStock() {
		List<Coche> coches = cocheDAO.findCarsInStock();
		final List<CocheDTO> cochesInStock = new ArrayList<>();
		coches.forEach(coche -> {
			final CocheDTO cocheDTO = map(coche);
			cochesInStock.add(cocheDTO);
		});
		return cochesInStock;
}


	@Override
	public void newSell(Integer idCoche, Integer idCliente, 		Integer idVendedor) throws NotFoundExcept {
		Coche soldCar = cocheDAO.findOne(idCoche);
		soldCar.setFechaVenta(todaysDate());
		addClienteToSoldCar(idCliente, soldCar);
		addVendedorToSoldCar(idVendedor, soldCar);
		cocheDAO.save(soldCar);
	}
	public void addClienteToSoldCar(Integer idCliente, Coche 			coche) throws NotFoundExcept {
		ClienteDTO clienteCoche = clienteService.findById			(idCliente);
			if(clienteCoche != null) {
		coche.setCliente(clienteService.map(clienteCoche));
}
}
		public void addVendedorToSoldCar(Integer idVendedor, 			Coche coche) throws NotFoundExcept {
			VendedorDTO vendedorCoche = 				vendedorService.findById(idVendedor);
			if(vendedorCoche != null) {
			coche.setVendedor(vendedorService.map			(vendedorCoche));
		}
}
		private String todaysDate() {
		Date today = Calendar.getInstance().getTime();
		return today.toString();
		}

}



	@Override
		public void createList(List<CocheDTO> listCocheDto) {
		for(CocheDTO cocheDTO : listCocheDto) {
		cocheDAO.save(map(cocheDTO));


	}
}
