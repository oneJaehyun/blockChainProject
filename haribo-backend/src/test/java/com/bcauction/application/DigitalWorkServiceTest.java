package com.bcauction.application;

import com.bcauction.Application;
import com.bcauction.domain.DigitalWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DigitalWorkServiceTest
{
	@Autowired
	private IDigitalWorkService digitalWorkService;

	@Transactional
	@Test
	public void test작품등록() {
		DigitalWork 작품 = new DigitalWork();
		작품.set이름("호롤롤롤로");
		작품.set회원id(15);

		DigitalWork 새작품 = this.digitalWorkService.작품등록(작품);
		assert 새작품.get공개여부().equals("Y");
		assert 새작품.get상태().equals("Y");
		assert 새작품.get회원id() == 15;
		assert 새작품.get이름().equals("호롤롤롤로");
		assert 새작품.getId() > 0;
	}


	@Test
	public void test목록조회() {
		List<DigitalWork> 작품목록 = this.digitalWorkService.목록조회();

		assert 작품목록.size() > 0;
	}

	@Test
	public void test조회() {
		DigitalWork 작품 = this.digitalWorkService.조회(1);

		assert 작품 != null;
		assert 작품.get회원id() == 4;
	}

	@Transactional
	@Test
	public void test수정() {

		DigitalWork 작품 = this.digitalWorkService.조회(1);
		작품.set이름("아아아");
		작품.set상태("N");
		작품.set공개여부("N");

		this.digitalWorkService.작품정보수정(작품);

		DigitalWork 수정된작품 = this.digitalWorkService.조회(1);
		assert 수정된작품.get이름().equals("아아아");
		assert 수정된작품.get상태().equals("N");
		assert 수정된작품.get공개여부().equals("N");
	}

	@Transactional
	@Test
	public void test삭제() {
		long 회원id = 4;
		this.digitalWorkService.작품삭제(회원id);

		DigitalWork 삭제된작품 = this.digitalWorkService.조회(회원id);
		assert 삭제된작품 == null;
	}
}
