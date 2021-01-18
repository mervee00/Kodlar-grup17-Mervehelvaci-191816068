package Helper;

import javax.swing.JOptionPane;

public class Helper {

	// Hata mesajlar� verir
	public static void showMsg(String str) {
		String msg;

		switch (str) {
		case "complete":
			msg = "L�tfen t�m alanlar� doldurunuz.";
			break;
		case "success":
			msg = "��lem ba�ar�yla yap�ld�.";
			break;
		case "cancel":
			msg = "��lem iptal edildi.";
			break;
		case "error":
			msg = "Bir hata olu�tu.";
			break;
		default:
			msg = str;
			break;
		}

		JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}

	// Kullan�c�ya i�lemi onaylay�p onaylamad���n� soran method
	public static boolean confirm(String str) {
		String msg2;
		switch (str) {
		case "sure":
			msg2 = "Bu i�lemi onayl�yor musunuz?";
			break;
		default:
			msg2 = str;
			break;
		}
		int resault = JOptionPane.showConfirmDialog(null, msg2, "Uyar�!", JOptionPane.YES_NO_OPTION);
		if (resault == 0)
			return true;
		else
			return false;
	}

	public static void helpMsg(String str) {
		String msg3;
		switch (str) {
		case "login":
			msg3 = "Hasta Giri�i:"
					+ "\n Hasta kullan�c� giri�i yapabilmek i�in kay�tl� olman�z gerekiyor."
					+ "\n Kay�tl� de�ilseniz kay�t ol butonuna bas�n."
					+ "\n Kay�tl� iseniz 11 haneli TC kimlik numaran�z� ve "
					+ "\n6 haneli �ifrenizi girip giri� yap butonuna bas�n�z.";
			break;
		case "register":
			msg3 = "Hasta Kay�t:"
					+ "\n Kay�t i�in b�t�n bilgileri doldurunuz.Kay�t butonuna bas�n�z."
					+ "\n Geri d�nmek i�in butona bas�n�z ";
			break;
		case "patient":
			msg3 = "Randevu olu�turma:"
					+ "\n Randevu olu�tur k�sm�ndan �nce poliklinik �e�iniz."
					+ "\n Randevu almak istedi�iniz b�l�m� se�erek doktor listesinin bulundu�u tablodan doktorunuzu �e�iniz."
					+ "\n Size uygun olan saati se�erek randevuyu al butonuna bas�n�z."
					+ "\n\nRandevu Silme:"
					+ "\n Randevular�m k�sm�ndan istedi�iniz randevuyu se�ip siliniz.";
			break;
		case "doctor":
			msg3 = "Randevu A�ma:"
					+ "\n Randevu olu�tur bol�m�nden �al��ma saatlerini se�iniz."
					+ "\n\nRandevu Silme:"
					+ "\n Dolu randevular b�l�m�nden dolu randevular�n�z� silebilirsiniz.";
			break;
		case "manager":
			msg3 = "Doktor Y�netim:"
					+ "\n Doktor ekleme, silme veye d�zenleme ekran�."
					+ "\n\nPoliklinikler"
					+ "\n Poliklinik ekleme, silme veye d�zenleme ekran�."
					+ "\n\nDoktor Randevular�"
					+ "\n Doktorlar�n randevular�n� silme ekran�.";
			break;
		default:
			msg3 = str;
			break;
		}
		JOptionPane.showMessageDialog(null, msg3, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}

}
