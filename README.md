# Collocation


Chương trình phát hiện collocation
================================================

(Release 28/2/2017).


I) TỔNG QUAN
-------------

	+) Chương trình collocation được sử dụng để phát hiện các collocation tiếng Việt (mã hóa bằng bảng mã Unicode UTF-8).
	
	+) Chương trình chạy dưới dạng dòng lệnh:
	 
		vào source code chạy lớp Run
		
	+) Yêu cầu: Máy cần cài JRE (Java Runtime Environment) phiên bản 1.6. JRE có thể tải về từ địa chỉ  website 
			Java của Sun Microsystems: http://java.sun.com/
	
II) DỮ LIỆU
------------

	Trong một lần chạy collocation có thể phát hiện ra các collocation từ một thư mục chứa các văn bản. 
		+) Kết quả: các file tách câu(bởi dấu câu . , ...); lọc ra các uni/bi/trigram; tập đầu ra các collocation được sắp xếp theo các chỉ số tscore, dice ...  
		  
		
III) CHẠY CHƯƠNG TRÌNH
-----------------------
			
	Chạy class Run -> hiện ra giao diện java swing cơ bản -> sử dụng các nút

IV) MỘT LƯU Ý , CÁC THƯ MỤC CẦN TẠO
	trong thư mục NLP_RESULT:
		tạo cands-nontokenized: chứa các ứng viên từ bộ dữ liệu không tách từ
		tạo cands-tokenized : chứa các ứng viên từ bộ dữ liệu tách từ
		tạo collocation-nontokenized: chứa các ứng viên được tính theo các chỉ số và sắp xếp theo thứ tự từ lớn đến bé từ bộ dữ liệu không tách từ
		tạo collocation-tokenized: chứa các ứng viên được tính theo các chỉ số và sắp xếp theo thứ tự từ lớn đến bé từ bộ dữ liệu tách từ
		tạo nontokenized: chứa các bi-trigram không tách từ
		tạo tokenized: chứa các bi-trigram tách từ
		tạo nontokenized-split: chứa các file tách câu theo dấu câu không có tách từ
		tạo tokenized-split: chứa các file tách câu theo dấu câu có tách từ
V) LIÊN HỆ
------------

	- Lê Hồng Phương <phuonglh@gmail.com>
	- Nguyễn Thị Minh Huyền <ntmhuyen@gmail.com>
	
	Khoa Toán-Cơ-Tin học, Trường Đại học Khoa học Tự nhiên, ĐHQG Hà Nội, Việt Nam.
	
	Website: http://www.loria.fr/~lehong/tools/vnTokenizer.php
