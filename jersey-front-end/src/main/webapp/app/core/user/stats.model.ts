export interface IStats {
  countProduit?: number;
  countFacture?: number;
  countClient?: number;
  sumDepense?: number;
}

export class Stats implements IStats {
  constructor(public countProduit?: number, public countFacture?: number, public countClient?: number, public sumDepense?: number) {
    this.countProduit = countProduit ? countProduit : null;
    this.countFacture = countFacture ? countFacture : null;
    this.countClient = countClient ? countClient : null;
    this.sumDepense = sumDepense ? sumDepense : null;
  }
}
