<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html"/>
  <xsl:template match="/">
    <xsl:for-each select="//*[local-name()='game']">
      <!--<a class="update-item">-->
        <!--<xsl:attribute name="href">DispatchServlet?btnAction=viewBook&amp;bookId=<xsl:value-of select="id" /></xsl:attribute>-->
        <img>
          <xsl:attribute name="src"><xsl:value-of select="//*[local-name()='thumbnail']" /></xsl:attribute>
        </img>
        <!--<div>-->
          <!--<xsl:value-of select="name" />-->
        <!--</div>-->
      <!--</a>-->
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>

