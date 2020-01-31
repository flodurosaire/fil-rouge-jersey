import { Component, OnInit, AfterViewInit, NgZone, OnDestroy, ViewEncapsulation } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account, UserService } from 'app/core';
import { Router } from '@angular/router';
import { IStats, Stats } from 'app/core/user/stats.model';
import * as am4core from '@amcharts/amcharts4/core';
import * as am4charts from '@amcharts/amcharts4/charts';
import am4themes_animated from '@amcharts/amcharts4/themes/animated';
import chartData from './chartdata';
// tslint:disable-next-line:no-unused-expression

am4core.useTheme(am4themes_animated);
am4core.options.autoSetClassName = true;

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['dashboard.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  account: Account;
  modalRef: NgbModalRef;
  stats: IStats;
  private chart: am4charts.XYChart;

  constructor(
    private accountService: AccountService,
    private userService: UserService,
    private loginModalService: LoginModalService,
    private zone: NgZone,
    private router: Router,
    private eventManager: JhiEventManager
  ) {
    this.stats = new Stats(0, 0, 0, 0);
  }

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.account = account;
      this.redirectIfUser(account);
    });
    this.registerAuthenticationSuccess();
    this.userService.getStats().subscribe(res => {
      this.stats = res.body;
    });
    this.userService.getRecetteChartData().subscribe(res => {
      let clientAddedByMonth: any[] = res.body.clientAddedByMonth;
      let sumDepensesByMonth: any[] = res.body.sumDepensesByMonth;
      let sumFacturesByMonth: any[] = res.body.sumFacturesByMonth;
      clientAddedByMonth.forEach(value => {
        chartData[value.mois - 1].nbrAddedClient = value.valeurMois;
      });
      sumDepensesByMonth.forEach(value => {
        chartData[value.mois - 1].totaldepense = value.valeurMois;
      });
      sumFacturesByMonth.forEach(value => {
        chartData[value.mois - 1].totalrecette = value.valeurMois;
      });

      // console.log('%c'+JSON.stringify(chartData), 'color:blue');
    });
  }
  ngAfterViewInit() {
    this.zone.runOutsideAngular(() => {
      // Create chart instance
      let chart = am4core.create('chartdiv', am4charts.XYChart);

      chart.colors.step = 2;
      chart.maskBullets = false;
      chart.data = chartData;
      // Create axes
      let moisAxis = chart.xAxes.push(new am4charts.CategoryAxis());
      moisAxis.dataFields.category = 'mois';
      moisAxis.renderer.grid.template.location = 0;
      moisAxis.renderer.minGridDistance = 50;
      moisAxis.renderer.grid.template.disabled = true;
      moisAxis.renderer.fullWidthTooltip = true;

      let recetteAxis = chart.yAxes.push(new am4charts.ValueAxis()); // distance
      recetteAxis.title.text = 'Recette';
      recetteAxis.renderer.grid.template.disabled = true;

      let depenseAxis = chart.yAxes.push(new am4charts.ValueAxis()); // duration
      depenseAxis.title.text = 'Depenses';
      depenseAxis.renderer.grid.template.disabled = true;
      depenseAxis.renderer.opposite = true;

      // durationAxis.durationFormatter.durationFormat = "hh'h' mm'min'";

      let clientAxis = chart.yAxes.push(new am4charts.ValueAxis()); // latitude -> nbrClient
      clientAxis.renderer.grid.template.disabled = true;
      clientAxis.renderer.labels.template.disabled = true;

      // Create series
      let recetteSeries = chart.series.push(new am4charts.ColumnSeries());
      recetteSeries.id = 'g1';
      recetteSeries.dataFields.valueY = 'totalrecette';
      recetteSeries.dataFields.categoryX = 'mois';
      recetteSeries.yAxis = recetteAxis;
      recetteSeries.tooltipText = '{valueY} euro';
      recetteSeries.name = 'totalrecette';
      recetteSeries.legendSettings.labelText = 'Total Recettes';
      recetteSeries.columns.template.fillOpacity = 0.7;

      let recetteState = recetteSeries.columns.template.states.create('hover');
      recetteState.properties.fillOpacity = 0.9;

      let depenseSeries = chart.series.push(new am4charts.LineSeries());
      depenseSeries.id = 'g3';
      depenseSeries.dataFields.valueY = 'totaldepense';
      depenseSeries.dataFields.categoryX = 'mois';
      depenseSeries.yAxis = depenseAxis;
      depenseSeries.name = 'totaldepense';
      depenseSeries.legendSettings.labelText = 'Total Depenses';
      depenseSeries.strokeWidth = 2;
      depenseSeries.tooltipText = '{valueY} euro';

      let depenseBullet = depenseSeries.bullets.push(new am4charts.Bullet());
      let depenseRectangle = depenseBullet.createChild(am4core.Rectangle);
      depenseBullet.horizontalCenter = 'middle';
      depenseBullet.verticalCenter = 'middle';
      depenseBullet.width = 7;
      depenseBullet.height = 7;
      depenseRectangle.width = 7;
      depenseRectangle.height = 7;

      let depenseState = depenseBullet.states.create('hover');
      depenseState.properties.scale = 1.2;

      let clientSeries = chart.series.push(new am4charts.LineSeries());
      clientSeries.id = 'g2';
      clientSeries.dataFields.valueY = 'nbrAddedClient';
      clientSeries.dataFields.categoryX = 'mois';
      clientSeries.yAxis = clientAxis;
      clientSeries.name = 'nbrAddedClient';
      clientSeries.legendSettings.labelText = 'Clients Ajoutés';
      clientSeries.strokeWidth = 2;
      clientSeries.tooltipText = '{valueY} clients';

      let clientBullet = clientSeries.bullets.push(new am4charts.CircleBullet());
      clientBullet.circle.fill = am4core.color('#fff');
      clientBullet.circle.strokeWidth = 2;
      clientBullet.circle.propertyFields.radius = 'nbrAddedClient';

      let clientState = clientBullet.states.create('hover');
      clientState.properties.scale = 1.2;

      let clientLabel = clientSeries.bullets.push(new am4charts.LabelBullet());
      // clientLabel.label.text = 'nbrAddedClient';
      clientLabel.label.horizontalCenter = 'left';
      clientLabel.label.dx = 14;

      // Add legend
      chart.legend = new am4charts.Legend();

      // Add cursor
      chart.cursor = new am4charts.XYCursor();
      chart.cursor.fullWidthLineX = true;
      chart.cursor.xAxis = moisAxis;
      chart.cursor.lineX.strokeOpacity = 0;
      chart.cursor.lineX.fill = am4core.color('#000');
      chart.cursor.lineX.fillOpacity = 0.1;

      this.chart = chart;
    });
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
        this.redirectIfUser(account);
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  redirectIfUser(account: Account) {
    if (account.authorities === null) {
      this.router.navigate(['']);
    }
    if (account.authorities.indexOf('ROLE_USER') > -1 && account.authorities.indexOf('ROLE_ADMIN') === -1) {
      this.router.navigate(['/facture']);
    }
  }
  ngOnDestroy() {
    this.zone.runOutsideAngular(() => {
      if (this.chart) {
        this.chart.dispose();
      }
    });
  }
}
