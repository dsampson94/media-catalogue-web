package za.co.sfy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import za.co.sfy.dataAccess.MediaCatalogueResource;
import za.co.sfy.domain.ApplicationSettings;
import za.co.sfy.domain.CD;
import za.co.sfy.domain.DVD;
import za.co.sfy.domain.MediaType;

@WebServlet("/server")
public class CatalogueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		new CommandFactory().runFactory(request.getParameter("mediaTypeBut"), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		new CommandFactory().runFactory(request.getParameter("tablebut"), request, response);
	}

	class CommandFactory {

		public void runFactory(String input, HttpServletRequest request, HttpServletResponse response) {
			switch (input) {
			case "getAllCDs":
				new RetrieveAllCDsCommand().executeCommand(request, response, "cdview.jsp");
				break;
			case "getAllDVDs":
				new RetrieveAllDVDsCommand().executeCommand(request, response, "dvdview.jsp");
				break;
			case "updatecd":
				new PrepareCDUpdateCommand().executeCommand(request, response, "updatecd.jsp");
				break;
			case "submitcdupdate":
				new CDUpdateCommand().executeCommand(request, response, "cdview.jsp");
				break;
			case "updatedvd":
				new PrepareDVDUpdateCommand().executeCommand(request, response, "updatedvd.jsp");
				break;
			case "submitdvdupdate":
				new DVDUpdateCommand().executeCommand(request, response, "dvdview.jsp");
				break;
			case "deletecd":
				new DeleteCDCommand().executeCommand(request, response, "cdview.jsp");
				break;
			case "deletedvd":
				new DeleteDVDCommand().executeCommand(request, response, "dvdview.jsp");
				break;
			case "addcd":
				new PrepareCDAddCommand().executeCommand(request, response, "cataloguecd.jsp");
				break;
			case "submitcdadd":
				new AddCDCommand().executeCommand(request, response, "cataloguehome.jsp");
				break;
			case "adddvd":
				new PrepareDVDAddCommand().executeCommand(request, response, "cataloguedvd.jsp");
				break;
			case "submitdvdadd":
				new AddDVDCommand().executeCommand(request, response, "cataloguehome.jsp");
				break;
			case "changesettings":
				new ThemeAdjustCommand().executeCommand(request, response, "websettings.jsp");
				break;
			}
		}
	}

	abstract class Command {

		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			try {
				request.getRequestDispatcher(pageName).forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	class RetrieveAllCDsCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			List<MediaType> retrieveAllOfTypeCD = new MediaCatalogueResource().retrieveAllOfType(new CD());
			request.getSession().setAttribute("cdlist", retrieveAllOfTypeCD);
			super.executeCommand(request, response, pageName);
		}
	}

	class RetrieveAllDVDsCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			List<MediaType> retrieveAllOfTypeDVD = new MediaCatalogueResource().retrieveAllOfType(new DVD());
			request.getSession().setAttribute("dvdlist", retrieveAllOfTypeDVD);
			super.executeCommand(request, response, pageName);
		}
	}

	class PrepareCDUpdateCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			String cdTitleU = (String) request.getParameter("cdtitle");
			int cdIdU = Integer.parseInt(request.getParameter("cdid"));
			CD cd = new CD();
			cd.setTitle(cdTitleU);
			cd.setId(cdIdU);
			MediaType retrievedMediaTypeCD = new MediaCatalogueResource().retrieveMediaType(cd);
			request.getSession().setAttribute("selectedCDTitle", retrievedMediaTypeCD.getTitle());
			request.getSession().setAttribute("selectedCD", retrievedMediaTypeCD);
			super.executeCommand(request, response, pageName);
		}
	}

	class PrepareDVDUpdateCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			String dvdTitleU = (String) request.getParameter("dvdtitle");
			int dvdIdU = Integer.parseInt(request.getParameter("dvdid"));
			DVD dvd = new DVD();
			dvd.setTitle(dvdTitleU);
			dvd.setId(dvdIdU);
			MediaType retrievedMediaTypeDVD = new MediaCatalogueResource().retrieveMediaType(dvd);
			request.getSession().setAttribute("selectedDVDTitle", retrievedMediaTypeDVD.getTitle());
			request.getSession().setAttribute("selectedDVD", retrievedMediaTypeDVD);
			super.executeCommand(request, response, pageName);
		}
	}

	class CDUpdateCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			CD cd = (CD) request.getSession().getAttribute("selectedCD");
			cd.setTitle(request.getParameter("titlefieldcd"));
			cd.setLength(((int) (Integer.parseInt((String) request.getParameter("lengthfieldcd")))));
			cd.setGenre(request.getParameter("genrefieldcd"));
			cd.setTracks(((int) (Integer.parseInt((String) request.getParameter("tracksfield")))));
			List<String> list = new ArrayList<String>();
			String parameter = request.getParameter("artistsfield");
			String[] split = parameter.split(",");
			for (String artist : split) {
				list.add(artist);
			}
			cd.setArtists(list);
			new MediaCatalogueResource().updateMediaType(cd);
			List<MediaType> retrieveAllOfTypeCDU = new MediaCatalogueResource().retrieveAllOfType(new CD());
			request.getSession().setAttribute("cdlist", retrieveAllOfTypeCDU);
			super.executeCommand(request, response, pageName);
		}
	}

	class DVDUpdateCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			DVD dvd = (DVD) request.getSession().getAttribute("selectedDVD");
			dvd.setTitle(request.getParameter("titlefield"));
			dvd.setLength(((int) (Integer.parseInt((String) request.getParameter("lengthfield")))));
			dvd.setGenre(request.getParameter("genrefield"));
			dvd.setLeadActor(request.getParameter("leadactorfield"));
			dvd.setLeadActress(request.getParameter("leadactressfield"));
			new MediaCatalogueResource().updateMediaType(dvd);
			List<MediaType> retrieveAllOfTypeDVDU = new MediaCatalogueResource().retrieveAllOfType(new DVD());
			request.getSession().setAttribute("dvdlist", retrieveAllOfTypeDVDU);
			super.executeCommand(request, response, pageName);
		}
	}

	class DeleteCDCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			String cdTitleD = (String) request.getParameter("cdtitle");
			CD cd = new CD();
			cd.setTitle(cdTitleD);
			new MediaCatalogueResource().deleteMediaType(cd);
			List<MediaType> retrieveAllOfTypeCD = new MediaCatalogueResource().retrieveAllOfType(new CD());
			request.getSession().setAttribute("cdlist", retrieveAllOfTypeCD);
			super.executeCommand(request, response, pageName);
		}
	}

	class DeleteDVDCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			String dvdTitleD = (String) request.getParameter("dvdtitle");
			DVD dvd = new DVD();
			dvd.setTitle(dvdTitleD);
			new MediaCatalogueResource().deleteMediaType(dvd);
			List<MediaType> retrieveAllOfTypeDVDD = new MediaCatalogueResource().retrieveAllOfType(new DVD());
			request.getSession().setAttribute("dvdlist", retrieveAllOfTypeDVDD);
			super.executeCommand(request, response, pageName);
		}
	}

	class PrepareCDAddCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			super.executeCommand(request, response, pageName);
		}
	}

	class PrepareDVDAddCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			super.executeCommand(request, response, pageName);
		}
	}

	class AddCDCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			CD cdadd = new CD();
			cdadd.setTitle(request.getParameter("titlefieldcdadd"));
			cdadd.setLength(((int) (Integer.parseInt((String) request.getParameter("lengthfieldcdadd")))));
			cdadd.setGenre(request.getParameter("genrefieldcdadd"));
			cdadd.setTracks(((int) (Integer.parseInt((String) request.getParameter("tracksfieldcdadd")))));
			List<String> lista = new ArrayList<String>();
			String parametera = request.getParameter("artistsfieldcdadd");
			String[] splita = parametera.split(",");
			for (String artist : splita) {
				lista.add(artist);
			}
			cdadd.setArtists(lista);
			new MediaCatalogueResource().createMediaType(cdadd);
			super.executeCommand(request, response, pageName);
		}
	}

	class AddDVDCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			DVD dvdadd = new DVD();
			dvdadd.setTitle(request.getParameter("titlefielddvdadd"));
			dvdadd.setLength(((int) (Integer.parseInt((String) request.getParameter("lengthfielddvdadd")))));
			dvdadd.setGenre(request.getParameter("genrefielddvdadd"));
			dvdadd.setLeadActor(request.getParameter("leadactorfielddvdadd"));
			dvdadd.setLeadActress(request.getParameter("leadactressfielddvdadd"));
			new MediaCatalogueResource().createMediaType(dvdadd);
			super.executeCommand(request, response, pageName);
		}
	}

	class ThemeAdjustCommand extends Command {

		@Override
		public void executeCommand(HttpServletRequest request, HttpServletResponse response, String pageName) {
			String colorVal = request.getParameter("colours");
			String fontVal = request.getParameter("fonts");
			ApplicationSettings aset = (ApplicationSettings) request.getSession().getAttribute("ApplicationSettings");
			aset.setBackgroundColour1(colorVal);
			aset.setFontStyle(fontVal);
			if (colorVal.equals("green")) {
				aset.setBackgroundColour2("lime");
			}
			if (colorVal.equals("blue")) {
				aset.setBackgroundColour2("aqua");
			}
			if (colorVal.equals("red")) {
				aset.setBackgroundColour2("maroon");
			}
			request.getSession().setAttribute("ApplicationSettings", aset);
			super.executeCommand(request, response, pageName);
		}
	}
}
