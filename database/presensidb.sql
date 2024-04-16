-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 21 Bulan Mei 2023 pada 13.23
-- Versi server: 10.4.24-MariaDB
-- Versi PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `presensidb`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `bagian`
--

CREATE TABLE `bagian` (
  `id_bagian` int(25) NOT NULL,
  `nama_bagian` varchar(100) NOT NULL,
  `keterangan_bagian` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `bagian`
--

INSERT INTO `bagian` (`id_bagian`, `nama_bagian`, `keterangan_bagian`) VALUES
(1, 'Bagian Hukum & HAM', 'Bagian Hukum & HAM');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jabatan`
--

CREATE TABLE `jabatan` (
  `id_jabatan` int(25) NOT NULL,
  `nama_jabatan` varchar(100) NOT NULL,
  `keterangan_jabatan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `jabatan`
--

INSERT INTO `jabatan` (`id_jabatan`, `nama_jabatan`, `keterangan_jabatan`) VALUES
(1, 'Staff', 'Staff');

-- --------------------------------------------------------

--
-- Struktur dari tabel `keteranganpresensi`
--

CREATE TABLE `keteranganpresensi` (
  `id_keterangan` int(10) NOT NULL,
  `keterangan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `keteranganpresensi`
--

INSERT INTO `keteranganpresensi` (`id_keterangan`, `keterangan`) VALUES
(1, 'Masuk'),
(2, 'Terlambat');

-- --------------------------------------------------------

--
-- Struktur dari tabel `lokasi`
--

CREATE TABLE `lokasi` (
  `id_lokasi` int(25) NOT NULL,
  `nama_lokasi` varchar(100) NOT NULL,
  `alamat` varchar(200) NOT NULL,
  `keterangan_lokasi` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `lokasi`
--

INSERT INTO `lokasi` (`id_lokasi`, `nama_lokasi`, `alamat`, `keterangan_lokasi`) VALUES
(1, 'gedung 1', 'bogor', 'gedung 1'),
(2, 'Gedung 2', 'Jakarta', 'gedung 2 di jakarta');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pegawai`
--

CREATE TABLE `pegawai` (
  `id_pegawai` varchar(50) NOT NULL,
  `nip` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` varchar(200) NOT NULL,
  `kota` varchar(100) NOT NULL,
  `jenis_kelamin` varchar(50) NOT NULL,
  `tgl_lahir` varchar(50) NOT NULL,
  `foto` varchar(100) NOT NULL,
  `terlambat` int(30) NOT NULL,
  `id_jabatan` int(25) NOT NULL,
  `id_bagian` int(25) NOT NULL,
  `id_shift` int(25) NOT NULL,
  `id_lokasi` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `pegawai`
--

INSERT INTO `pegawai` (`id_pegawai`, `nip`, `nama`, `alamat`, `kota`, `jenis_kelamin`, `tgl_lahir`, `foto`, `terlambat`, `id_jabatan`, `id_bagian`, `id_shift`, `id_lokasi`) VALUES
('1', '00001', 'Firmansyah', '-', '-', 'Laki - Laki', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('2', '00002', 'Hanum Pratiwi', '-', '-', 'Perempuan', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('3', '00003', 'Abdullah Azam Rabbani', '-', '-', 'Laki - Laki', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('4', '00004', 'Ryfka Fawzy', '-', '-', 'Perempuan', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('5', '00005', 'Ika Mustika Wulan', '-', '-', 'Perempuan', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('6', '00006', 'Rangga Saputra', '-', '-', 'Laki - Laki', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('7', '00007', 'Mahpudin', '-', '-', 'Laki - Laki', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('8', '00008', 'Muhammad Fikri', '-', '-', 'Laki - Laki', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1),
('9', '00009', 'Muhammad Fahri', '-', '-', 'Laki - Laki', 'May 1, 2023', 'kosong', 0, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `presensikeluar`
--

CREATE TABLE `presensikeluar` (
  `id_pkeluar` int(100) NOT NULL,
  `id_presensi` int(100) NOT NULL,
  `jam_keluar` varchar(100) NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `presensikeluar`
--

INSERT INTO `presensikeluar` (`id_pkeluar`, `id_presensi`, `jam_keluar`, `keterangan`) VALUES
(1, 3, '17:28:25', 'Sesudah Waktu Keluar');

-- --------------------------------------------------------

--
-- Struktur dari tabel `presensimasuk`
--

CREATE TABLE `presensimasuk` (
  `id_presensi` int(100) NOT NULL,
  `id_pegawai` varchar(50) NOT NULL,
  `tanggal_presen` varchar(50) NOT NULL,
  `bulan_presen` varchar(50) NOT NULL,
  `tahun_presen` varchar(50) NOT NULL,
  `jam_presen` varchar(50) NOT NULL,
  `id_keterangan` int(10) NOT NULL,
  `terlambat` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `presensimasuk`
--

INSERT INTO `presensimasuk` (`id_presensi`, `id_pegawai`, `tanggal_presen`, `bulan_presen`, `tahun_presen`, `jam_presen`, `id_keterangan`, `terlambat`) VALUES
(1, '1', '16', '5', '2023', '13:30:43', 2, 0),
(2, '1', '17', '5', '2023', '09:51:36', 2, 1),
(3, '3', '21', '5', '2023', '05:31:27', 2, 1),
(4, '4', '21', '5', '2023', '05:31:31', 2, 1),
(5, '2', '21', '5', '2023', '05:31:34', 2, 1),
(6, '1', '21', '5', '2023', '17:27:57', 2, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `shift`
--

CREATE TABLE `shift` (
  `id_shift` int(25) NOT NULL,
  `nama_shift` varchar(100) NOT NULL,
  `waktu_masuk` varchar(50) NOT NULL,
  `waktu_keluar` varchar(50) NOT NULL,
  `keterangan_shift` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `shift`
--

INSERT INTO `shift` (`id_shift`, `nama_shift`, `waktu_masuk`, `waktu_keluar`, `keterangan_shift`) VALUES
(1, 'Pagi', '05:00:19', '18:00:19', ''),
(2, 'Sore', '16:00:19', '21:00:19', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `role`) VALUES
(1, '123', '123', 1),
(2, '321', '321', 1),
(3, 'agung', '317e3e5de76f3a2cfe9de0a1aed573219b0a1b4458237f07d44725fa66cd9141', 2);

-- --------------------------------------------------------

--
-- Stand-in struktur untuk tampilan `v_pegawai`
-- (Lihat di bawah untuk tampilan aktual)
--
CREATE TABLE `v_pegawai` (
`id_pegawai` varchar(50)
,`nip` varchar(50)
,`nama` varchar(50)
,`alamat` varchar(200)
,`kota` varchar(100)
,`jenis_kelamin` varchar(50)
,`tgl_lahir` varchar(50)
,`foto` varchar(100)
,`nama_jabatan` varchar(100)
,`nama_bagian` varchar(100)
,`nama_lokasi` varchar(100)
,`nama_shift` varchar(100)
,`waktu_masuk` varchar(50)
,`waktu_keluar` varchar(50)
);

-- --------------------------------------------------------

--
-- Stand-in struktur untuk tampilan `v_presensikeluar`
-- (Lihat di bawah untuk tampilan aktual)
--
CREATE TABLE `v_presensikeluar` (
`id_pkeluar` int(100)
,`id_presensi` int(100)
,`nip` varchar(50)
,`nama` varchar(50)
,`foto` varchar(100)
,`nama_jabatan` varchar(100)
,`nama_bagian` varchar(100)
,`nama_lokasi` varchar(100)
,`nama_shift` varchar(100)
,`waktu_masuk` varchar(50)
,`waktu_keluar` varchar(50)
,`tanggal_presen` varchar(50)
,`bulan_presen` varchar(50)
,`tahun_presen` varchar(50)
,`jam_presen` varchar(50)
,`jam_keluar` varchar(100)
,`keterangan` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in struktur untuk tampilan `v_presensimasuk`
-- (Lihat di bawah untuk tampilan aktual)
--
CREATE TABLE `v_presensimasuk` (
`id_presensi` int(100)
,`nip` varchar(50)
,`nama` varchar(50)
,`foto` varchar(100)
,`nama_jabatan` varchar(100)
,`nama_bagian` varchar(100)
,`nama_lokasi` varchar(100)
,`nama_shift` varchar(100)
,`waktu_masuk` varchar(50)
,`waktu_keluar` varchar(50)
,`tanggal_presen` varchar(50)
,`bulan_presen` varchar(50)
,`tahun_presen` varchar(50)
,`jam_presen` varchar(50)
,`keterangan` varchar(50)
);

-- --------------------------------------------------------

--
-- Struktur untuk view `v_pegawai`
--
DROP TABLE IF EXISTS `v_pegawai`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_pegawai`  AS SELECT `pegawai`.`id_pegawai` AS `id_pegawai`, `pegawai`.`nip` AS `nip`, `pegawai`.`nama` AS `nama`, `pegawai`.`alamat` AS `alamat`, `pegawai`.`kota` AS `kota`, `pegawai`.`jenis_kelamin` AS `jenis_kelamin`, `pegawai`.`tgl_lahir` AS `tgl_lahir`, `pegawai`.`foto` AS `foto`, `jabatan`.`nama_jabatan` AS `nama_jabatan`, `bagian`.`nama_bagian` AS `nama_bagian`, `lokasi`.`nama_lokasi` AS `nama_lokasi`, `shift`.`nama_shift` AS `nama_shift`, `shift`.`waktu_masuk` AS `waktu_masuk`, `shift`.`waktu_keluar` AS `waktu_keluar` FROM ((((`pegawai` join `jabatan` on(`pegawai`.`id_jabatan` = `jabatan`.`id_jabatan`)) join `bagian` on(`pegawai`.`id_bagian` = `bagian`.`id_bagian`)) join `lokasi` on(`pegawai`.`id_lokasi` = `lokasi`.`id_lokasi`)) join `shift` on(`pegawai`.`id_shift` = `shift`.`id_shift`))  ;

-- --------------------------------------------------------

--
-- Struktur untuk view `v_presensikeluar`
--
DROP TABLE IF EXISTS `v_presensikeluar`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_presensikeluar`  AS SELECT `presensikeluar`.`id_pkeluar` AS `id_pkeluar`, `presensimasuk`.`id_presensi` AS `id_presensi`, `pegawai`.`nip` AS `nip`, `pegawai`.`nama` AS `nama`, `pegawai`.`foto` AS `foto`, `jabatan`.`nama_jabatan` AS `nama_jabatan`, `bagian`.`nama_bagian` AS `nama_bagian`, `lokasi`.`nama_lokasi` AS `nama_lokasi`, `shift`.`nama_shift` AS `nama_shift`, `shift`.`waktu_masuk` AS `waktu_masuk`, `shift`.`waktu_keluar` AS `waktu_keluar`, `presensimasuk`.`tanggal_presen` AS `tanggal_presen`, `presensimasuk`.`bulan_presen` AS `bulan_presen`, `presensimasuk`.`tahun_presen` AS `tahun_presen`, `presensimasuk`.`jam_presen` AS `jam_presen`, `presensikeluar`.`jam_keluar` AS `jam_keluar`, `presensikeluar`.`keterangan` AS `keterangan` FROM (((((((`presensikeluar` join `presensimasuk` on(`presensikeluar`.`id_presensi` = `presensimasuk`.`id_presensi`)) join `pegawai` on(`presensimasuk`.`id_pegawai` = `pegawai`.`id_pegawai`)) join `jabatan` on(`pegawai`.`id_jabatan` = `jabatan`.`id_jabatan`)) join `bagian` on(`pegawai`.`id_bagian` = `bagian`.`id_bagian`)) join `lokasi` on(`pegawai`.`id_lokasi` = `lokasi`.`id_lokasi`)) join `shift` on(`pegawai`.`id_shift` = `shift`.`id_shift`)) join `keteranganpresensi` on(`presensimasuk`.`id_keterangan` = `keteranganpresensi`.`id_keterangan`))  ;

-- --------------------------------------------------------

--
-- Struktur untuk view `v_presensimasuk`
--
DROP TABLE IF EXISTS `v_presensimasuk`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_presensimasuk`  AS SELECT `presensimasuk`.`id_presensi` AS `id_presensi`, `pegawai`.`nip` AS `nip`, `pegawai`.`nama` AS `nama`, `pegawai`.`foto` AS `foto`, `jabatan`.`nama_jabatan` AS `nama_jabatan`, `bagian`.`nama_bagian` AS `nama_bagian`, `lokasi`.`nama_lokasi` AS `nama_lokasi`, `shift`.`nama_shift` AS `nama_shift`, `shift`.`waktu_masuk` AS `waktu_masuk`, `shift`.`waktu_keluar` AS `waktu_keluar`, `presensimasuk`.`tanggal_presen` AS `tanggal_presen`, `presensimasuk`.`bulan_presen` AS `bulan_presen`, `presensimasuk`.`tahun_presen` AS `tahun_presen`, `presensimasuk`.`jam_presen` AS `jam_presen`, `keteranganpresensi`.`keterangan` AS `keterangan` FROM ((((((`presensimasuk` join `pegawai` on(`presensimasuk`.`id_pegawai` = `pegawai`.`id_pegawai`)) join `jabatan` on(`pegawai`.`id_jabatan` = `jabatan`.`id_jabatan`)) join `bagian` on(`pegawai`.`id_bagian` = `bagian`.`id_bagian`)) join `lokasi` on(`pegawai`.`id_lokasi` = `lokasi`.`id_lokasi`)) join `shift` on(`pegawai`.`id_shift` = `shift`.`id_shift`)) join `keteranganpresensi` on(`presensimasuk`.`id_keterangan` = `keteranganpresensi`.`id_keterangan`))  ;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `bagian`
--
ALTER TABLE `bagian`
  ADD PRIMARY KEY (`id_bagian`);

--
-- Indeks untuk tabel `jabatan`
--
ALTER TABLE `jabatan`
  ADD PRIMARY KEY (`id_jabatan`);

--
-- Indeks untuk tabel `keteranganpresensi`
--
ALTER TABLE `keteranganpresensi`
  ADD PRIMARY KEY (`id_keterangan`);

--
-- Indeks untuk tabel `lokasi`
--
ALTER TABLE `lokasi`
  ADD PRIMARY KEY (`id_lokasi`);

--
-- Indeks untuk tabel `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`id_pegawai`),
  ADD KEY `id_jabatan` (`id_jabatan`),
  ADD KEY `id_bagian` (`id_bagian`,`id_shift`,`id_lokasi`),
  ADD KEY `id_shift` (`id_shift`),
  ADD KEY `id_lokasi` (`id_lokasi`);

--
-- Indeks untuk tabel `presensikeluar`
--
ALTER TABLE `presensikeluar`
  ADD PRIMARY KEY (`id_pkeluar`),
  ADD KEY `id_presensi` (`id_presensi`);

--
-- Indeks untuk tabel `presensimasuk`
--
ALTER TABLE `presensimasuk`
  ADD PRIMARY KEY (`id_presensi`),
  ADD KEY `id_pegawai` (`id_pegawai`),
  ADD KEY `id_keterangan` (`id_keterangan`);

--
-- Indeks untuk tabel `shift`
--
ALTER TABLE `shift`
  ADD PRIMARY KEY (`id_shift`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `pegawai`
--
ALTER TABLE `pegawai`
  ADD CONSTRAINT `pegawai_ibfk_1` FOREIGN KEY (`id_bagian`) REFERENCES `bagian` (`id_bagian`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pegawai_ibfk_2` FOREIGN KEY (`id_shift`) REFERENCES `shift` (`id_shift`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pegawai_ibfk_3` FOREIGN KEY (`id_lokasi`) REFERENCES `lokasi` (`id_lokasi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pegawai_ibfk_4` FOREIGN KEY (`id_jabatan`) REFERENCES `jabatan` (`id_jabatan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `presensikeluar`
--
ALTER TABLE `presensikeluar`
  ADD CONSTRAINT `presensikeluar_ibfk_1` FOREIGN KEY (`id_presensi`) REFERENCES `presensimasuk` (`id_presensi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `presensimasuk`
--
ALTER TABLE `presensimasuk`
  ADD CONSTRAINT `presensimasuk_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id_pegawai`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `presensimasuk_ibfk_2` FOREIGN KEY (`id_keterangan`) REFERENCES `keteranganpresensi` (`id_keterangan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
