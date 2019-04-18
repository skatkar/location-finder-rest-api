package cs.locationfinder.fixture;

import java.util.ArrayList;
import java.util.List;

import cs.locationfinder.vo.LocationVO;

public interface LocationFinderFixture {
	public static List<LocationVO> getLocationList(){
		List<LocationVO> locationLost = new ArrayList<LocationVO>();
		LocationVO locationVO = new LocationVO();
		locationVO.setId("fe26287a-3938-4d70-b616-57257e383237");
		locationVO.setLatitude(37.124);
		locationVO.setLongitude(-117.23);
		locationVO.setLocationType("School");
		locationVO.setCity("Fullerton");
		locationVO.setDescription("Famous School");
		locationLost.add(locationVO);
		return locationLost;
	}
	
	public static LocationVO getLocation() {
		LocationVO locationVO = new LocationVO();
		locationVO.setId("fe26287a-3938-4d70-b616-57257e383237");
		locationVO.setLatitude(37.124);
		locationVO.setLongitude(-117.23);
		locationVO.setLocationType("School");
		locationVO.setCity("Fullerton");
		locationVO.setDescription("Famous School");
		return locationVO;
		
	}
}
