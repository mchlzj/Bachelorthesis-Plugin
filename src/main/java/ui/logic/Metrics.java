package ui.logic;

public class Metrics {

		private double NOHCDVal = 0;		
		private double NOIDVal = 0;
		private double ROIVal = 0;
		private double NOMDVal = 0;		
		private double ROMVal = 0;
		private double NDVal = 0;
		private double ROTVal = 0;
		
		public Metrics(double NOHCD, double NOID,  double NOMD, double NDVal) {
			this.NOHCDVal = NOHCD;
			this.NOIDVal = NOID;
			this.NOMDVal = NOMD;
			this.NDVal = NDVal;

			if(isROIValid()) {				
				this.ROIVal = (this.NOIDVal + this.NOMDVal + this.NDVal) / (this.NOHCDVal + this.NOIDVal + this.NOMDVal + this.NDVal);
			}
			if(isROMValid())  {
				this.ROMVal = (this.NOMDVal + this.NDVal)/ (this.NOHCDVal + this.NOIDVal + this.NOMDVal + this.NDVal);
			}
			if(isROTValid()) {
				this.ROTVal = (this.NDVal)/ (this.NOHCDVal + this.NOIDVal + this.NOMDVal + this.NDVal);
			}
		}
		public boolean isValid() {
			return  isROIValid() && isROMValid() && isROTValid();
			
		}
		public boolean isROTValid() {
			return this.NOHCDVal  != 0 || this.NOIDVal != 0 || NOMDVal != 0 || this.NDVal != 0;
		}
		
		public boolean isROMValid() {
			return this.NOHCDVal  != 0 || this.NOIDVal != 0 || this.NOMDVal != 0;
		}
		
		public boolean isROIValid() {
			return this.NOHCDVal  != 0 || this.NOIDVal != 0;
		}

		public double getNOHCDVal() {
			return NOHCDVal;
		}

		public double getNOIDVal() {
			return NOIDVal;
		}

		public double getROIVal() {
			return ROIVal;
		}

		public double getNOMDVal() {
			return NOMDVal;
		}

		public double getROMVal() {
			return ROMVal;
		}
		
		public double getNDVal() {
			return NDVal;
		}

		public double getROTVal() {
			return ROTVal;
		}

		
}
