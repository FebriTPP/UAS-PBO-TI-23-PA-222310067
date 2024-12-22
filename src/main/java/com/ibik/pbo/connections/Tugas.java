package com.ibik.pbo.connections;

public class Tugas {
	
    private int id_tugas;
    private String judul;
    private String deskripsi;
    private String namaFile;
    
    public String getNamaFile() {
		return namaFile;
	}
	public void setNamaFile(String namaFile) {
		this.namaFile = namaFile;
	}
	public byte[] getFile_tugas() {
		return file_tugas;
	}
	public void setFile_tugas(byte[] file_tugas) {
		this.file_tugas = file_tugas;
	}
	private byte[] file_tugas;
    

    public int getId_tugas() {
		return id_tugas;
	}
	public void setId_tugas(int id_tugas) {
		this.id_tugas = id_tugas;
	}
	
	
	public String getJudul() {
		return judul;
	}
	public void setJudul(String judul) {
		this.judul = judul;
	}
	public String getDeskripsi() {
		return deskripsi;
	}
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}
	
    
    

}
