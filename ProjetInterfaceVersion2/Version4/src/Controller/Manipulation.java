package Controller;

import java.util.ArrayList;
import Modele.FabriqueModele;
import Modele.FigureColoree;
import Modele.Point;
import Modele.Polygone;
import application.MainController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Manipulation {

	FabriqueModele fm;
	FabriqueFigure ff;
	boolean Endeplacement,updatableColor;
	ArrayList<FigureColoree> forms;
	int FormId;
//	private GraphicsContext MainController.gc;
	private double sourisX,sourisY;

	//	Canvas canvas;
	//	
	public Manipulation(FabriqueModele fmm/*,Canvas c*/,FabriqueFigure ff) {
		fm=fmm;
		//		this.canvas=c;
		this.ff=ff;
		forms=new ArrayList<FigureColoree>();
		Endeplacement=false;
		updatableColor=false;
	}
	//����¼��ķ���
	public void ClickChoose(MouseEvent e) {
		forms=fm.getFigures();
		boolean isAccesse=false;
		DeSelectionnerTous();
		sourisX=e.getX();
		sourisY=e.getY();
		for(int i=0;i<forms.size();i++) {
			if(forms.get(i)!=null&&((Polygone)forms.get(i)).Accesseur(sourisX, sourisY)){
				forms.get(i).setEnSelection(true);
				isAccesse=true;
				((Polygone)forms.get(i)).afficher(MainController.gc);
				break;
			}
		}
//		System.out.println(((Polygone)fm.getFigureenCours()));
		if(!isAccesse) {//��������λ��������ͼ�ε��ⲿ����ȡ��ѡ��
			DeSelectionnerTous();
		}
	}

	public void Pressed(MouseEvent e) {
		forms=fm.getFigures();
		//		forms.add(fm.getFigureenCours());
		sourisX=e.getX();
		sourisY=e.getY();

		for(int i=0;i<forms.size();i++) {
			if(forms.get(i)!=null&&((Polygone)forms.get(i)).Accesseur(sourisX, sourisY)){
				this.daplacable(true);//�����϶�ͼ��
				this.Updatable(true);//���Ը�����ɫ
				FormId=i;
				break;
			}
		}
	}

	public void dragFigure(MouseEvent e) {
		if(Endeplacement) {
			double dx=e.getX()-sourisX;//�ƶ���x����
			double dy=e.getY()-sourisY;//�ƶ���y����
			FigureColoree f=forms.get(FormId);
			Point[] pointsVertex=((Polygone)f).getPointsVertex();
			for(int i=0;i<pointsVertex.length;i++) {
				pointsVertex[i].setX(pointsVertex[i].RendreX()+dx);
				pointsVertex[i].setY(pointsVertex[i].RendreY()+dy);
			}
			((Polygone)f).setPointsVertex(pointsVertex);
			forms.set(FormId, f);
			sourisX=e.getX();
			sourisY=e.getY();
			this.ff.EffaceretDessiner();
		}
	}
	public void lacher() {//�ͷ����ʱ���������϶�
		this.daplacable(false);
	}
	
	public void daplacable(boolean res) {
		Endeplacement=res;
	}
	
	//������ɫ
	public void upDateColor(Color c) {
		if(this.updatableColor) {
			this.forms.get(FormId).setColorCorant(c);
		}
//		System.out.println(this.updatableColor);
		this.ff.EffaceretDessiner();
	}
	
	
	public void Updatable(boolean res) {
		this.updatableColor=res;
	}
	public boolean getUpdatable() {
		return this.updatableColor;
	}

	public void DeSelectionnerTous() {
		this.fm.DeSelectionnerAll();
		this.ff.EffaceretDessiner();
	}
}

