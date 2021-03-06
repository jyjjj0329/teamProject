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

import kr.project.DAO.BuyerDAO;
import kr.project.DAO.SellerGdsDAO;
import kr.project.VO.BuyerVO;
import kr.project.VO.SellerVO;

@Controller
public class SignUpController {
	
//	SqlSession을 사용하기 위해 변수를 만들고 다른 설정 없이도 자동으로 실행되게 @Autowired 해줌.
	@Autowired
	public SqlSession sqlSession;
	
	
//	회원가입 버튼 클릭시
	@RequestMapping(value = "/signUp")
	public String signUp() {
		
		return "signUp/signUp";
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
		String extension = ".png";
//		경로
		String filePath = "C:\\Users\\CHOYEJI\\git\\teamProject\\teamProject\\src\\main\\webapp\\resources\\storeImage\\" + store + "\\";
//		가게 사진 이름
		String storeimg_Name = "storeImg";
		System.out.println(filePath);
		
//		폴더 없으면 생성
		File fileDirectory = new File(filePath);
		if(!fileDirectory.exists()) {
			fileDirectory.mkdir();
			System.out.println("폴더 생성!!!");
		}
//		list배열로 파일들 다 받음
		List<MultipartFile> fileList = mtp.getFiles("storeImg[]");
		System.out.println("컨트롤러에서 fileList의 size의 값은 : " + fileList.size());
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
				System.out.println(extension);
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
	
//	소비자 로그인
	@RequestMapping(value = "/buyerLoginResult")
	public String buyerLoginResult(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("id", id);
		hmap.put("pw", pw);
		
		BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
		int buyerResult = mapper.buyerLogin(hmap);
		
		HttpSession session = req.getSession();
		if(buyerResult == 1) {
			session.setAttribute("buyer_id", id);
			session.setAttribute("buyer_pw", pw);
			System.out.println("sessionScope.buyer_id의 값은 : " + session.getAttribute("buyer_id"));
		}
		
		model.addAttribute("buyerResult", buyerResult);
		return "login/loginResult";
	}
	
//	판매자 로그인
	@RequestMapping(value = "/sellerLoginResult")
	public String sellerLoginResult(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("id", id);
		hmap.put("pw", pw);
		
		SellerGdsDAO mapper = sqlSession.getMapper(SellerGdsDAO.class);
		int sellerResult = mapper.sellerLogin(hmap);
		
		HttpSession session = req.getSession();
		if(sellerResult == 1) {
			session.setAttribute("seller_id", id);
			session.setAttribute("pw", pw);
		}
		
		model.addAttribute("sellerResult", sellerResult);
		return "login/loginResult";
	}
	
// ----------------------------------------판매자 페이지 끝	
	
/** 소비자 회원가입 페이지 호출 */
	@RequestMapping(value = "/buyerSignUp")
	public String buyerSignUp() {
		System.out.println("소비자 회원가입 페이지로 들어옴");
		return "signUp/buyerSignUp";
	}

	
/** ID 중복체크 페이지 호출 */
	@RequestMapping(value = "/buyerCheckID")
	public String buyerCheckID(HttpServletRequest req, Model model) {
		System.out.println("컨트롤러에서 buyerCheckID 들어옴.");
		
		/** ID 값을 받고, 받은 ID 값을 DB내의 ID 값과 대조하여
		 *  일치하는 ID의 개수를 반환받아 출력 */
		String id = req.getParameter("id");
		System.out.println("buyerCheckID에서 id의 값은 : " + id);
		BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
		int result = mapper.CheckID(id);
		System.out.println("buyerCheckID에서 result의 값은 : " + result);
		
		model.addAttribute("result", result);
		model.addAttribute("id", id);
		
		return "signUp/buyerCheckID";
	}
	
/** 별명 중복체크 페이지 호출 */
	@RequestMapping(value = "/buyerCheckNickname")
	public String buyerCheckNickname(HttpServletRequest req, Model model) {
		System.out.println("컨트롤러에서 buyerCheckNickname 들어옴.");
		
		/** nickname 값을 받고, 받은 nickname 값을 DB내의 nickname 값과 대조하여
		 *  일치하는 nickname의 개수를 반환받아 출력 */
		String nickname = req.getParameter("nickname");
		System.out.println("buyerCheckNickname에서 nickname의 값은 : " + nickname);
		BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
		int result = mapper.CheckNickname(nickname);
		System.out.println("buyerCheckNickname에서 result의 값은 : " + result);
		
		model.addAttribute("result", result);
		model.addAttribute("nickname", nickname);
		
		return "signUp/buyerCheckNickname";
	}
	
/**	소비자 회원가입 완료 페이지 */
	@RequestMapping(value = "/buyerSignUpOK")
	public String buyerSignUpOK(HttpServletRequest req, BuyerVO buyerVO, 
			MultipartHttpServletRequest mtp) {
		
		BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
		
		
/**		합쳐서 넣어줘야 하는 것들 따로 값을 넣어줌 */
		
		/**이메일 주소*/
		String email = req.getParameter("email1") + "@" + req.getParameter("email2");
		buyerVO.setEmail(email); 
		/**통신사 및 휴대폰 번호*/
		String carrier = req.getParameter("carrier");
		buyerVO.setCarrier(carrier);
		String Phone = req.getParameter("phonenum");
		buyerVO.setPhonenum(Phone);
		/**카드 번호*/
		String creditcard = req.getParameter("creditcard");
		buyerVO.setCreditcard(creditcard);
		String cardNum = req.getParameter("cardNum1") + req.getParameter("cardNum2")
		+ req.getParameter("cardNum3") + req.getParameter("cardNum4");
		System.out.println("cardNum의 값은 : " + cardNum);
		buyerVO.setCardNum(cardNum);
		/**우편번호 및 주소*/
		String address = "(" + req.getParameter("postcode") + ")" + req.getParameter("address1")
		 + req.getParameter("address2") + " " + req.getParameter("address3");
		buyerVO.setAddress(address);
		
		System.out.println("컨트롤러에서 buyerVO의 값은 : " + buyerVO.toString());

		mapper.buyerInsert(buyerVO);
		
		/**회원가입 완료 후 메인 페이지로 되돌아감*/

		return "main/mainpage";
	}
	
	
	
//	로그아웃
	@RequestMapping(value="/logout")
	public String buyerLogout(HttpServletRequest req) {
		System.out.println("컨트롤러에서 logout에 들어옴");
		
		HttpSession session = req.getSession();
		session.removeAttribute("buyer_id");
		session.removeAttribute("seller_id");
		session.removeAttribute("pw");
		
		return "login/logout";
	}
}
