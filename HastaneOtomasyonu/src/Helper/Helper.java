package Helper;

import javax.swing.JOptionPane;

public class Helper {

	// Hata mesajlarý verir
	public static void showMsg(String str) {
		String msg;

		switch (str) {
		case "complete":
			msg = "Lütfen tüm alanlarý doldurunuz.";
			break;
		case "success":
			msg = "Ýþlem baþarýyla yapýldý.";
			break;
		case "cancel":
			msg = "Ýþlem iptal edildi.";
			break;
		case "error":
			msg = "Bir hata oluþtu.";
			break;
		default:
			msg = str;
			break;
		}

		JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}

	// Kullanýcýya iþlemi onaylayýp onaylamadýðýný soran method
	public static boolean confirm(String str) {
		String msg2;
		switch (str) {
		case "sure":
			msg2 = "Bu iþlemi onaylýyor musunuz?";
			break;
		default:
			msg2 = str;
			break;
		}
		int resault = JOptionPane.showConfirmDialog(null, msg2, "Uyarý!", JOptionPane.YES_NO_OPTION);
		if (resault == 0)
			return true;
		else
			return false;
	}

	public static void helpMsg(String str) {
		String msg3;
		switch (str) {
		case "login":
			msg3 = "Hasta Giriþi:"
					+ "\n Hasta kullanýcý giriþi yapabilmek için kayýtlý olmanýz gerekiyor."
					+ "\n Kayýtlý deðilseniz kayýt ol butonuna basýn."
					+ "\n Kayýtlý iseniz 11 haneli TC kimlik numaranýzý ve "
					+ "\n6 haneli þifrenizi girip giriþ yap butonuna basýnýz.";
			break;
		case "register":
			msg3 = "Hasta Kayýt:"
					+ "\n Kayýt için bütün bilgileri doldurunuz.Kayýt butonuna basýnýz."
					+ "\n Geri dönmek için butona basýnýz ";
			break;
		case "patient":
			msg3 = "Randevu oluþturma:"
					+ "\n Randevu oluþtur kýsmýndan önce poliklinik þeçiniz."
					+ "\n Randevu almak istediðiniz bölümü seçerek doktor listesinin bulunduðu tablodan doktorunuzu þeçiniz."
					+ "\n Size uygun olan saati seçerek randevuyu al butonuna basýnýz."
					+ "\n\nRandevu Silme:"
					+ "\n Randevularým kýsmýndan istediðiniz randevuyu seçip siliniz.";
			break;
		case "doctor":
			msg3 = "Randevu Açma:"
					+ "\n Randevu oluþtur bolümünden çalýþma saatlerini seçiniz."
					+ "\n\nRandevu Silme:"
					+ "\n Dolu randevular bölümünden dolu randevularýnýzý silebilirsiniz.";
			break;
		case "manager":
			msg3 = "Doktor Yönetim:"
					+ "\n Doktor ekleme, silme veye düzenleme ekraný."
					+ "\n\nPoliklinikler"
					+ "\n Poliklinik ekleme, silme veye düzenleme ekraný."
					+ "\n\nDoktor Randevularý"
					+ "\n Doktorlarýn randevularýný silme ekraný.";
			break;
		default:
			msg3 = str;
			break;
		}
		JOptionPane.showMessageDialog(null, msg3, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}

}
