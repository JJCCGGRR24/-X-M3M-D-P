
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Explorer;
import domain.Finder;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private FinderService	finderService;

	// Service aux
	@Autowired
	private ExplorerService	explorerService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		final Explorer explorer = this.explorerService.create();
		Assert.notNull(explorer.getFinder());

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final List<Finder> lf = (List<Finder>) this.finderService.findAll();
		final Finder f = this.finderService.findOne(lf.get(0).getId());
		Assert.notNull(f);

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final List<Finder> lc = (List<Finder>) this.finderService.findAll();
		Assert.notNull(lc);

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		final List<Explorer> lex = (List<Explorer>) this.explorerService.findAll();
		final Explorer e = lex.get(0);

		final Finder f = e.getFinder();

		this.authenticate(f.getExplorer().getUserAccount().getUsername());

		f.setKeyword("aaaaaaaaaaaaaa");

		this.finderService.save(f);

		this.unauthenticate();
		Assert.isTrue(f.getKeyword() == "aaaaaaaaaaaaaa");

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCache() {
		System.out.println("========== testCache() ==========");

		final List<Explorer> lex = (List<Explorer>) this.explorerService.findAll();
		final Explorer e = lex.get(0);

		this.authenticate(e.getUserAccount().getUsername());
		final Finder finder = e.getFinder();
		finder.setTrips(new ArrayList<Trip>());
		finder.setDateMax(null);
		finder.setDateMin(null);
		finder.setKeyword("Title 1");
		finder.setMaximumPrice(null);
		finder.setMinimumPrice(null);
		final Date now = new Date();
		finder.setMomentCache(now);
		final Finder saved = this.finderService.save(finder);

		this.finderService.updateCache(saved);
		System.out.println("No ha pasado el tiempo de cache y la cache esta vacia:");
		System.out.println(saved.getTrips());

		final Date after = new Date();
		after.setHours(after.getHours() - 1);
		after.setMinutes(after.getMinutes() - 2);
		saved.setMomentCache(after);
		final Finder saved2 = this.finderService.save(finder);
		this.finderService.updateCache(saved2);
		System.out.println("Ha pasado el tiempo de cache y esta se actualiza:");
		System.out.println(saved2.getTrips());

		this.unauthenticate();

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testFinder() {
		System.out.println("========== testFinder() ==========");

		final List<Explorer> lex = (List<Explorer>) this.explorerService.findAll();
		final Explorer e = lex.get(0);
		final Finder finder = e.getFinder();

		finder.setDateMax(null);
		finder.setDateMin(null);
		finder.setKeyword("Title 1");
		finder.setMaximumPrice(null);
		finder.setMinimumPrice(null);

		List<Trip> lt = this.finderService.findTripsByFinder(finder);
		System.out.println(lt.toString());
		Assert.notNull(lt);

		System.out.println("---------");

		finder.setDateMax(null);
		finder.setDateMin(null);
		finder.setKeyword("171020-KLJP");
		finder.setMaximumPrice(null);
		finder.setMinimumPrice(null);

		lt = this.finderService.findTripsByFinder(finder);
		System.out.println(lt.toString());
		Assert.notNull(lt);

		System.out.println("---------");

		finder.setDateMax(null);
		finder.setDateMin(null);
		finder.setKeyword(null);
		finder.setMaximumPrice(1300.0);
		finder.setMinimumPrice(20.0);

		lt = this.finderService.findTripsByFinder(finder);
		System.out.println(lt.toString());
		Assert.notNull(lt);

		System.out.println("---------");

		final Date damax = new Date();
		damax.setMonth(11);
		damax.setDate(1);
		finder.setDateMax(damax);
		final Date damin = new Date();
		damin.setMonth(9);
		damin.setDate(1);
		finder.setDateMin(damin);
		finder.setKeyword(null);
		finder.setMaximumPrice(null);
		finder.setMinimumPrice(null);

		lt = this.finderService.findTripsByFinder(finder);
		System.out.println(lt.toString());
		Assert.notNull(lt);

	}

}
