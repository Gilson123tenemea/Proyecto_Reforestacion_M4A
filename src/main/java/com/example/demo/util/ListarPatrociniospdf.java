package com.example.demo.util;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("verpatrocinios")
public class ListarPatrociniospdf extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Obtén la lista de patrocinios del modelo
        List<Map<String, Object>> patrocinios = (List<Map<String, Object>>) model.get("patrocinios");

        // Agregar título
        document.add(new Paragraph("Reporte de Patrocinios"));

        // Iterar sobre la lista de patrocinios y agregar la información al documento
        for (Map<String, Object> patrocinio : patrocinios) {
            document.add(new Paragraph("Beneficios: " + patrocinio.get("beneficios")));
            document.add(new Paragraph("Cantidad Estimada: " + patrocinio.get("cantiad_estimada")));
            document.add(new Paragraph("Detalle: " + patrocinio.get("detalledpnado")));
            document.add(new Paragraph("Fecha Inicio: " + patrocinio.get("fechainicio")));
            document.add(new Paragraph("Fecha Fin: " + patrocinio.get("fechafin")));
            document.add(new Paragraph("Tipo de Patrocinio: " + patrocinio.get("tipo_patrocinio")));
            document.add(new Paragraph("Nombre de la Empresa: " + patrocinio.get("nombreEmpresa")));
            document.add(new Paragraph(" ")); // Espacio en blanco
        }
    }
}
