import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;

public class TokoRoti extends JFrame {
    private JTextField fieldJenisRoti, fieldHargaRoti;
    private JTextArea areaDaftarRoti, areaHasilSorting;
    private JButton tombolTambah, tombolUrutkan, tombolKeluar;
    private JComboBox<String> pilihanKriteria;

    private ArrayList<Roti> daftarRoti = new ArrayList<>();

    public TokoRoti() {
        setTitle("Teddy's Bakery");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // Panel Input Roti
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelJenisRoti = new JLabel("Jenis Roti:");
        JLabel labelHargaRoti = new JLabel("Harga Roti:");
        fieldJenisRoti = new JTextField();
        fieldHargaRoti = new JTextField();
        tombolTambah = new JButton("Tambah Roti");
        panelInput.add(labelJenisRoti);
        panelInput.add(fieldJenisRoti);
        panelInput.add(labelHargaRoti);
        panelInput.add(fieldHargaRoti);
        panelInput.add(new JLabel()); // Spacer
        panelInput.add(tombolTambah);

        // Panel Daftar dan Hasil
        JPanel panelDaftarHasil = new JPanel(new GridLayout(1, 2, 5, 5));
        panelDaftarHasil.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel Daftar Roti
        JPanel panelDaftar = new JPanel(new BorderLayout(5, 5));
        JLabel labelDaftarRoti = new JLabel("Daftar Roti:");
        areaDaftarRoti = new JTextArea(15, 30);
        areaDaftarRoti.setEditable(false);
        panelDaftar.add(labelDaftarRoti, BorderLayout.NORTH);
        panelDaftar.add(new JScrollPane(areaDaftarRoti), BorderLayout.CENTER);

        // Panel Hasil Pengurutan
        JPanel panelHasil = new JPanel(new BorderLayout(5, 5));
        JLabel labelHasil = new JLabel("Hasil Pengurutan:");
        areaHasilSorting = new JTextArea(15, 30);
        areaHasilSorting.setEditable(false);
        panelHasil.add(labelHasil, BorderLayout.NORTH);
        panelHasil.add(new JScrollPane(areaHasilSorting), BorderLayout.CENTER);

        // Tambahkan panel daftar dan hasil ke satu panel
        panelDaftarHasil.add(panelDaftar);
        panelDaftarHasil.add(panelHasil);

        // Panel Sorting
        JPanel panelSorting = new JPanel(new GridLayout(2, 2, 5, 5));
        panelSorting.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelKriteria = new JLabel("Urutkan berdasarkan:");
        String[] kriteria = {"Jenis Roti (Bubble Sort)", "Harga Roti (Selection Sort)"};
        pilihanKriteria = new JComboBox<>(kriteria);
        pilihanKriteria.setMaximumRowCount(2);
        tombolUrutkan = new JButton("Urutkan");
        tombolKeluar = new JButton("Keluar");
        panelSorting.add(labelKriteria);
        panelSorting.add(pilihanKriteria);
        panelSorting.add(tombolUrutkan);
        panelSorting.add(tombolKeluar);

        // Tambahkan ke Frame
        add(panelInput, BorderLayout.NORTH);
        add(panelDaftarHasil, BorderLayout.CENTER);
        add(panelSorting, BorderLayout.SOUTH);

        // Action Listeners
        tombolTambah.addActionListener(this::tambahRoti);
        tombolUrutkan.addActionListener(this::urutkanDaftar);
        tombolKeluar.addActionListener(e -> System.exit(0));
    }

    private void tambahRoti(ActionEvent e) {
        try {
            String jenisRoti = fieldJenisRoti.getText().trim();
            double hargaRoti = Double.parseDouble(fieldHargaRoti.getText().trim());

            if (jenisRoti.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jenis roti tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            daftarRoti.add(new Roti(jenisRoti, hargaRoti));
            fieldJenisRoti.setText("");
            fieldHargaRoti.setText("");

            tampilkanDaftarRoti();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga roti harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilkanDaftarRoti() {
        StringBuilder sb = new StringBuilder();
        for (Roti roti : daftarRoti) {
            sb.append(roti).append("\n");
        }
        areaDaftarRoti.setText(sb.toString());
    }

    private void urutkanDaftar(ActionEvent e) {
        if (daftarRoti.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Daftar roti kosong.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String pilihan = (String) pilihanKriteria.getSelectedItem();

        if (pilihan.equals("Jenis Roti (Bubble Sort)")) {
            bubbleSort(daftarRoti, Comparator.comparing(Roti::getJenisRoti));
        } else if (pilihan.equals("Harga Roti (Selection Sort)")) {
            selectionSort(daftarRoti, Comparator.comparingDouble(Roti::getHarga));
        }

        tampilkanHasilSorting();
    }

    private void tampilkanHasilSorting() {
        StringBuilder sb = new StringBuilder();
        for (Roti roti : daftarRoti) {
            sb.append(roti).append("\n");
        }
        areaHasilSorting.setText(sb.toString());
    }

    private void bubbleSort(ArrayList<Roti> list, Comparator<Roti> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    Roti temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    private void selectionSort(ArrayList<Roti> list, Comparator<Roti> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Roti temp = list.get(minIndex);
            list.set(minIndex, list.get(i));
            list.set(i, temp);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TokoRoti frame = new TokoRoti();
            frame.setVisible(true);
        });
    }
}

class Roti {
    private String jenisRoti;
    private double harga;

    public Roti(String jenisRoti, double harga) {
        this.jenisRoti = jenisRoti;
        this.harga = harga;
    }

    public String getJenisRoti() {
        return jenisRoti;
    }

    public double getHarga() {
        return harga;
    }

    @Override
    public String toString() {
        return jenisRoti + ", Harga: Rp " + harga;
    }
}
