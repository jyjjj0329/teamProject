package kr.project.Controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.project.DAO.SellerGdsDAO;
import kr.project.VO.SellerVO;

@Controller
public class SignUpController {
	
//	SqlSession을 사용하기 위해 변수를 만들고 다른 설정 없이도 자동으로 실행되게 @Autowired 해줌.
	@Autowired
	public SqlSession sqlSession;
	
	@RequestMapping(value = "/dd")
	public String home() {
		
		return "index";
	}
	
//	회원가입 버튼 클릭시
	@RequestMapping(value = "/signUp")
	public String signUp() {
		
		return "signUp/signUp";
	}
	
//	소비자 회원가입
	@RequestMapping(value = "/buyerSignUp")
	public String buyerSignUp() {
		return "signUp/buyerSignUp";
	}
	
//	판매자 회원가입
	@RequestMapping(value = "/sellerSignUp")
	public String sellerSignUp() {
		
		return "signUp/sellerSignUp";
	}
	
//	ID 중복체크
	@RequestMapping(value = "/sellerCheckID")
	public String sellerCheckID(HttpServletRequest req, Model model) {
		System.out.println("컨트롤러에서 sellerCheckID 들어옴.");
		
		String id = req.getParameter("id");
		System.out.println("sellerCheckID에서 id의 값은 : " + id);
		SellerGdsDAO mapper = sqlSession.getMapper(SellerGdsDAO.class);
		int result = mapper.CheckID(id);
		System.out.println("sellerCheckID에서 result의 값은 : " + result);
		
		model.addAttribute("result", result);
		model.addAttribute("id", id);
		
		return "signUp/sellerCheckID";
	}
	
//	판매자 회원가입
	int i = 1;
	@RequestMapping(value = "/sellerSignUpOK")
	public String sellerSignUpOK(HttpServletRequest req, SellerVO sellerVO, 
			MultipartHttpServletRequest mtp) {
		
//		합쳐서 넣어줘야 하는것들 따로 값을 넣어줌.
		String email = req.getParameter("email1") + "@" + req.getParameter("email2");
		sellerVO.setEmail(email);
		String Phone = "(" + req.getParameter("phone") + ")" + req.getParameter("phonenum");
		sellerVO.setPhonenum(Phone);
		String address = "(" + req.getParameter("postcode") + ")" + req.getParameter("address1")
		 + req.getParameter("address3");
		sellerVO.setStoreaddress(address);
		
		String store = req.getParameter("store");
		
		/* 파일 추가 부분. */
//		확장자
		String extension = null;
//		경로
		String filePath = "C:\\Users\\Administrator\\Desktop\\teamProject2\\src\\main\\webapp\\resources\\storeImage/" + store + "/";
//		가게 사진 이름
		String storeimg_Name = "사진 안올림";
		System.out.println(filePath);
		
//		폴더 없으면 생성
		File fileDirectory = new File(filePath);
		if(!fileDirectory.exists()) {
			fileDirectory.mkdir();
			System.out.println("폴더 생성!!!");
		}
		
//		list배열로 파일들 다 받음
		List<MultipartFile> fileList = mtp.getFiles("storeImg[]");
		
//		fileList의 사이즈가 0보다 크면(파일이 있으면) 밑에를 실행해라.
		if(fileList.size() > 0) {
			storeimg_Name = i + "-";
			int j = 1;
			for(MultipartFile mf : fileList) {
				String originFileName = mf.getOriginalFilename();
				if(originFileName == "") {
					System.out.println("for문나감");
					break;
				}
				extension = originFileName.substring(originFileName.lastIndexOf("."),
						originFileName.length());
				System.out.println("컨트롤러에서 storeimg_Name의 값은 : " + storeimg_Name);
//				이 경로에 파일을 보냄.
				String safeFile = filePath + storeimg_Name + j + extension;
				System.out.println("컨트롤러에서 safeFile의 값은 : " + safeFile);
				try {
					mf.transferTo(new File(safeFile));
					System.out.println("업로드 완료");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("업로드 실패");
				}
				j++;
			}
			i++;
		}
//		imgName, extension 저장
		sellerVO.setStoreimg_Name(storeimg_Name);
		System.out.println("컨트롤러에서 storeimg_Name의 값은 : " + storeimg_Name);
		sellerVO.setExtension(extension);
		System.out.println("컨트롤러에서 extension의 값은 : " + extension);
		SellerGdsDAO mapper = sqlSession.getMapper(SellerGdsDAO.class);
		
		mapper.sellerInsert(sellerVO);
		
		System.out.println("컨트롤러에서 sellerVO의 값은 : " + sellerVO.toString());
		
		return "main/mainpage";
	}
	
//	로그인 페이지 호출
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest req, Model model) {
		return "login/login";
	}
	
//	로그인
	@RequestMapping(value = "/loginResult")
	public String loginResult(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("id", id);
		hmap.put("pw", pw);
		
		SellerGdsDAO mapper = sqlSession.getMapper(SellerGdsDAO.class);
//		임시로 로그인으로 만들었지만 sellerLogin입니다.
		int result = mapper.login(hmap);
		
		if(result == 1) {
			HttpSession session = req.getSession();
			session.setAttribute("seller_id", id);
			session.setAttribute("seller_pw", pw);
		}
		
		model.addAttribute("result", result);
		return "login/loginResult";
	}
	
}